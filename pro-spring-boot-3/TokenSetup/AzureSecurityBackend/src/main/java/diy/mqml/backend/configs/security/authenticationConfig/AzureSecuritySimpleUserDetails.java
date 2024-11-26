package diy.mqml.backend.configs.security.authenticationConfig;

import diy.mqml.backend.configs.security.userConfig.SecuritySimpleUser;
import diy.mqml.backend.configs.security.userConfig.SecuritySimpleUserDetails;
import diy.mqml.backend.configs.security.userConfig.SecuritySimpleUserSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Map;

public class AzureSecuritySimpleUserDetails extends SecuritySimpleUserDetails implements OidcUser {

    private final OidcUser oidcUser;

    public AzureSecuritySimpleUserDetails(SecuritySimpleUser user,
                                          Collection<? extends GrantedAuthority> authorities,
                                          OidcIdToken oidcIdToken) {
        super(user, authorities);
        this.oidcUser = new DefaultOidcUser(authorities, oidcIdToken);
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.oidcUser.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.oidcUser.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.oidcUser.getIdToken();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.oidcUser.getAttributes();
    }

    @Override
    public SecuritySimpleUserSession getUserSession() {
        OidcIdToken token = getIdToken();
        final SecuritySimpleUserSession userSession = super.getUserSession();
        return SecuritySimpleUserSession.builder()
                .id(userSession.getId())
                .authenticatedDateTime(userSession.getAuthenticatedDateTime())
                .issuedDateTime(userSession.getIssuedDateTime())
                .expiredDateTime(userSession.getExpiredDateTime())
                .build();

    }

}
