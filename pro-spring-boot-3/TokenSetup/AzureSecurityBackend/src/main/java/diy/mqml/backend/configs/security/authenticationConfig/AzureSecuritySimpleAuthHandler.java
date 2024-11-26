package diy.mqml.backend.configs.security.authenticationConfig;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface AzureSecuritySimpleAuthHandler {
    void accept(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
}
