package diy.mqml.backend.configs.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * this filter is used to capture user information,
 * after successfully authenticated by the authorization servers such as Azure/Google
 *
 * */

public class UserInformationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Retrieve authentication from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof OidcUser) {
                // OIDC user details
                OidcUser oidcUser = (OidcUser) principal;
                String email = oidcUser.getEmail();
                String fullName = oidcUser.getFullName();
                System.out.println("OIDC User Info: " + fullName + " (" + email + ")");
            } else if (principal instanceof DefaultOAuth2User) {
                // OAuth2 user details
                DefaultOAuth2User oauth2User = (DefaultOAuth2User) principal;
                System.out.println("OAuth2 User Info: " + oauth2User.getAttributes());
            } else if (principal instanceof Jwt) {
                // JWT user details
                Jwt jwt = (Jwt) principal;
                System.out.println("JWT User Info: " + jwt.getClaims());
            } else {
                // Other principal types
                System.out.println("Principal Info: " + principal.toString());
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
