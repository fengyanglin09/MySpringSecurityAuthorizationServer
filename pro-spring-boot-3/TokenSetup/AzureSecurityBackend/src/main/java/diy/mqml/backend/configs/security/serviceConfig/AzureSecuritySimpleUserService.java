package diy.mqml.backend.configs.security.serviceConfig;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;



/**
 *
 * this method will be called after the user is successfully authenticated,
 * this method is used to put the users infos to the security context
 *
 * */


@Service
public class AzureSecuritySimpleUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {



    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        return null;
    }
}
