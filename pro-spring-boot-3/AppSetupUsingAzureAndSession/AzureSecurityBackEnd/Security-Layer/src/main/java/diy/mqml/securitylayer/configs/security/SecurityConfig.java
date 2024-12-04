package diy.mqml.securitylayer.configs.security;

import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadWebApplicationHttpSecurityConfigurer;
import diy.mqml.securitylayer.configs.security.authenticationConfig.AzureSecuritySimpleAuthSuccessHandler;
import diy.mqml.securitylayer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

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
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    /**
     * Add configuration logic as needed.
     */
    @Bean
    SecurityFilterChain authenticationFilterChain(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource
    ) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource)); // Attach CORS config

        http.csrf(AbstractHttpConfigurer::disable);

        http.with(AadWebApplicationHttpSecurityConfigurer.aadWebApplication(), Customizer.withDefaults());

        http.oauth2Login(config-> config.successHandler(new AzureSecuritySimpleAuthSuccessHandler((request, response, user)->{
                    //todo - define the logic to add users to database
                        log.debug("User {} successfully logged in", user.getFullName());
                        this.userService.registerUser(user);
                })));

//        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource));

        http.anonymous(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(request->request
//                .requestMatchers("/actuator/**").hasRole("ACTUATOR")
                .requestMatchers("users/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/auth-status/**").permitAll()
                .requestMatchers("/welcome-page/**").authenticated()
                .anyRequest().authenticated());


        return http.build();
    }



}
