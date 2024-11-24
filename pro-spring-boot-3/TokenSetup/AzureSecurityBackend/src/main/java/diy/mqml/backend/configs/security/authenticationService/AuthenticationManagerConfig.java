package diy.mqml.backend.configs.security.authenticationService;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class AuthenticationManagerConfig {

    private final CustomOidcAuthenticationProvider customOidcAuthenticationProvider;

    public AuthenticationManagerConfig(CustomOidcAuthenticationProvider customOidcAuthenticationProvider) {
        this.customOidcAuthenticationProvider = customOidcAuthenticationProvider;
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
//        ((org.springframework.security.authentication.ProviderManager) authenticationManager)
//                .getProviders().add(customOidcAuthenticationProvider);
        return authenticationManager;
    }

}
