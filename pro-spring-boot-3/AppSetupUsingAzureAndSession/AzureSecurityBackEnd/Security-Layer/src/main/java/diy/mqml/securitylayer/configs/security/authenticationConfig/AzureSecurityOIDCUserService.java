package diy.mqml.securitylayer.configs.security.authenticationConfig;

import diy.mqml.securitylayer.configs.security.userConfig.SecuritySimpleUser;
import diy.mqml.securitylayer.configs.security.userConfig.azureGraphService.AzureGraphClient;
import diy.mqml.securitylayer.configs.security.userConfig.azureGraphService.AzureWebSecurityGraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 *
 * this method will be called after the user is successfully authenticated,
 * this method is used to put the users infos to the security context
 *
 * */


@Service
@Slf4j
public class AzureSecurityOIDCUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OidcUserService oidcUserService = new OidcUserService();

    private final AzureWebSecurityGraphService azureWebSecurityGraphService;

    private final String rolePrefix;


    /**
     *
     * By making this parameter optional, the constructor ensures that even if GrantedAuthorityDefaults is not configured,
     * the code will still work by falling back to the default prefix "ROLE_".
     *
     * */

    public AzureSecurityOIDCUserService( AzureWebSecurityGraphService azureWebSecurityGraphService,
                                        Optional<GrantedAuthorityDefaults> grantedAuthorityDefaults
    ) {

        this.azureWebSecurityGraphService = azureWebSecurityGraphService;
        this.rolePrefix = grantedAuthorityDefaults.map(GrantedAuthorityDefaults::getRolePrefix).orElse("ROLE_");
    }


    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);
        if (oidcUser == null) {
            throw new InternalAuthenticationServiceException("Could not retrieve user");
        }

        final SecuritySimpleUser securitySimpleUser = getUser(oidcUser);

        final List<SimpleGrantedAuthority> authorities = securitySimpleUser.getRoles().stream().map(this::createAuthority).toList();

        return new AzureSecurityOIDCUserDetails(securitySimpleUser, authorities, oidcUser.getIdToken());
    }


    /**
     * 1. After the AzureSecurityOIDCUserService successfully processes the user details,
     * the final user object (typically an implementation of OidcUser) is placed into the Spring Security SecurityContext.
     *
     * 2. The SecurityContext is part of the thread-local storage during the lifecycle of a request,
     * allowing authentication details to be accessed throughout the application.
     *
     * 3. Within the SecurityContext, user details are encapsulated inside an Authentication object,
     * which represents the authenticated user's credentials and authorities.
     *
     * 4. The user details returned by loadUser (in this case, an instance of AzureSecurityOIDCUserDetails) are used.
     *
     * 5. The AzureSecurityOIDCUserDetails is ultimately wrapped inside the Authentication object and set in the SecurityContextHolder,
     * allowing it to be retrieved anywhere in the application.
     *
     * */
    private SecuritySimpleUser getUser(OidcUser oidcUser) {
        final AzureGraphClient graphClient = azureWebSecurityGraphService.getGraphClient(oidcUser);


        final SecuritySimpleUser.SecuritySimpleUserBuilder securitySimpleUserBuilder = SecuritySimpleUser.builder()
                .id(oidcUser.getClaimAsString("oid"))
                .systemId(oidcUser.getClaimAsString("onprem_sid"))
                .fullName(oidcUser.getFullName())
                .lastName(oidcUser.getFamilyName())
                .firstName(oidcUser.getGivenName())
                .emailAddress(oidcUser.getEmail())
                .roles(oidcUser.getClaimAsStringList("roles"));

        this.loadUserInfoFromGraph(securitySimpleUserBuilder, graphClient);
        this.loadUserPhotoFromGraph(securitySimpleUserBuilder, graphClient);

        return securitySimpleUserBuilder.build();
    }


    /**
     * Load user info from graph client
     *
     * @param userBuilder User to build
     * @param graphClient Graph client for retrieving information
     */
    private void loadUserInfoFromGraph(SecuritySimpleUser.SecuritySimpleUserBuilder userBuilder,
                                       AzureGraphClient graphClient) {
        try {
            graphClient.findCurrentUser()
                    .ifPresentOrElse(user -> {
                                userBuilder.id(user.id());
                                userBuilder.lanId(user.lanId());
                                userBuilder.systemId(user.systemId());
                                userBuilder.personId(user.personId());
                                userBuilder.fullName(user.fullName());
                                userBuilder.lastName(user.lastName());
                                userBuilder.firstName(user.firstName());
                                userBuilder.department(user.department());
                                userBuilder.jobTitle(user.jobTitle());
                                userBuilder.workSiteState(user.workSiteState());
                                userBuilder.workSiteCity(user.workSiteCity());
                                userBuilder.mailLocation(user.mailLocation());
                                userBuilder.emailAddress(user.emailAddress());
                            },
                            () -> {
                                throw new InternalAuthenticationServiceException("Current user not found");
                            }
                    );
        } catch (Exception ex) {
            log.error("Error retrieving user info from graph client", ex);
            throw new InternalAuthenticationServiceException("Error retrieving user info");
        }
    }

    /**
     * Load photo from the graph client
     *
     * @param userBuilder User to build
     * @param graphClient Graph client for retrieving information
     */
    private void loadUserPhotoFromGraph(SecuritySimpleUser.SecuritySimpleUserBuilder userBuilder,
                                        AzureGraphClient graphClient) {
        try {
            graphClient.findUserPhoto().ifPresent(userBuilder::photo);
        } catch (Exception ex) {
            log.error("Error retrieving user photo from graph client", ex);
        }
    }

    /**
     * Create granted authority using the configurable role prefix or default 'ROLE_'
     *
     * @param name Granted authority name.
     * @return SimpleGrantedAuthority
     */
    private SimpleGrantedAuthority createAuthority(String name) {
        final String prefix = this.rolePrefix != null ? this.rolePrefix : "";
        return new SimpleGrantedAuthority(prefix.concat(name));
    }
    
}
