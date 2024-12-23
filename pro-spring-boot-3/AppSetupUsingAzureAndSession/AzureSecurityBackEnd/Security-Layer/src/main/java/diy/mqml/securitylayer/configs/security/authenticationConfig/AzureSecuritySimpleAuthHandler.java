package diy.mqml.securitylayer.configs.security.authenticationConfig;


import diy.mqml.securitylayer.configs.security.userConfig.SecuritySimpleUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface AzureSecuritySimpleAuthHandler {
    void accept(HttpServletRequest request, HttpServletResponse response, SecuritySimpleUser user);
}
