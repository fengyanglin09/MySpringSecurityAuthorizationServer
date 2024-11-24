package diy.mqml.backend.configs.security.authenticationService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class CustomOidcAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof OAuth2LoginAuthenticationToken oauth2Auth) {

            // Extract OIDC user details
//            OidcUser oidcUser = (OidcUser) oauth2Auth.getPrincipal();
//
//            // Custom logic for authentication or user details processing
//            String email = oidcUser.getEmail();
//            String name = oidcUser.getFullName();
//            System.out.println("Authenticated user: " + name + " (" + email + ")");

            // Return the original authentication if everything is valid
            return authentication;
        }

        // Return null if this provider does not support the authentication type
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
