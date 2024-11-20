package diy.mqml.backend.configs.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CorsConfigurationSource corsConfigurationSource
                                                   ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(config->config.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(Customizer.withDefaults()) // For login flows
        ;

        return http.build();
    }

    /**
     *
     * ClientRegistrationRepository: This is injected to find client registration details, which include information about your OAuth2 provider (in this case, azure).
     * NimbusJwtDecoder: This is a standard class from the Spring Security library to decode JWT tokens using the JWK (JSON Web Key) set URI.
     * findByRegistrationId("azure"): This is used to retrieve the ClientRegistration associated with the "azure" registration ID from the ClientRegistrationRepository.
     *
     * */
    @Bean
    public JwtDecoder jwtDecoder(ClientRegistrationRepository clientRegistrationRepository) {
        var clientRegistration = clientRegistrationRepository.findByRegistrationId("azure");
        return NimbusJwtDecoder.withJwkSetUri(clientRegistration.getProviderDetails().getJwkSetUri()).build();
    }

}
