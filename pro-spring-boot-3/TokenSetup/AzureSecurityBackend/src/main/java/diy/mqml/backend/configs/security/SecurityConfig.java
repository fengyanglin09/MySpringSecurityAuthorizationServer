package diy.mqml.backend.configs.security;

import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadResourceServerHttpSecurityConfigurer;
import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadWebApplicationHttpSecurityConfigurer;
import diy.mqml.backend.configs.security.authenticationService.CustomOidcAuthenticationProvider;
import diy.mqml.backend.configs.security.filter.UserInformationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

/**
 *
 * Overview
 *
 * Two Security Filter Chains:
 *
 * apiFilterChain: Configures the resource server functionality, applying OAuth 2.0 token-based security for API endpoints (/api/**).
 * htmlFilterChain: Configures web application security for HTML endpoints, focusing on user authentication via Azure AD login.
 * Separation of Concerns:
 *
 * The @Order(1) annotation ensures that apiFilterChain is evaluated first for requests matching /api/**.
 * Requests not matching /api/** fall through to the htmlFilterChain, which handles typical web application traffic.
 *
 * */

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService(); // Default implementation of OIDC user service
    }
    /**
     * Add configuration logic as needed.
     */
    @Bean
//    @Order(2)
    SecurityFilterChain authenticationFilterChain(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource,
            CustomOidcAuthenticationProvider oidcAuthenticationProvider
    ) throws Exception {
        http.with(AadWebApplicationHttpSecurityConfigurer.aadWebApplication(), Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource));

        http.anonymous(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(request->request
                .requestMatchers("/auth-status/**").permitAll()
                .anyRequest().authenticated());

//        http.authenticationProvider(oidcAuthenticationProvider);
//        http.authenticationManager(authenticationManager);

//        http.addFilterAfter(new UserInformationFilter(), OAuth2AuthorizationRequestRedirectFilter.class);

        // Do some custom configuration.
        return http.build();
    }

//    //Purpose: Protects API endpoints by enforcing bearer token-based authentication.
//    @Bean
//    @Order(1)
//    public SecurityFilterChain apiFilterChain(HttpSecurity http,
//                                              CorsConfigurationSource corsConfigurationSource) throws Exception {
//        http.with(AadResourceServerHttpSecurityConfigurer.aadResourceServer(), Customizer.withDefaults());
//
//        http.csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource))
//                .authorizeHttpRequests( auth -> auth.requestMatchers("/h2-console/**").permitAll())
//                .authorizeHttpRequests( auth -> auth.requestMatchers("/api/**").authenticated())
//                .authorizeHttpRequests( auth -> auth.anyRequest().authenticated());
//
//        return http.build();
//    }
//
//
//    //Purpose: Secures web application routes, allowing unauthenticated access to specific routes like /login while protecting all others.
//    @Bean
//    @Order(2)
//    public SecurityFilterChain htmlFilterChain(HttpSecurity http,
//                                               CorsConfigurationSource corsConfigurationSource
//                                               ) throws Exception {
//
//
//        http.with(AadWebApplicationHttpSecurityConfigurer.aadWebApplication(), Customizer.withDefaults());
//
//
//        http.csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource));
//
//
//        http.authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll())
//                .oauth2Login(oauth -> oauth.loginPage("/oauth2/authorization/azure"));
//        ;
//
//        return http.build();
//    }
}
