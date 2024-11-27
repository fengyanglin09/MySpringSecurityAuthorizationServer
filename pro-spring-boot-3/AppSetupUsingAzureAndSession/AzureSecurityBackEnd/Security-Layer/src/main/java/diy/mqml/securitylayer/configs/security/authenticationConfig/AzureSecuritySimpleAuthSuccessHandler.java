package diy.mqml.securitylayer.configs.security.authenticationConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;


@Slf4j
public class AzureSecuritySimpleAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final AzureSecuritySimpleAuthHandler azureSecuritySimpleAuthHandler;

    public AzureSecuritySimpleAuthSuccessHandler(AzureSecuritySimpleAuthHandler azureSecuritySimpleAuthHandler) {
        this.azureSecuritySimpleAuthHandler = azureSecuritySimpleAuthHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
            super.onAuthenticationSuccess(request, response, authentication);
            if(this.azureSecuritySimpleAuthHandler != null && authentication != null){
                this.azureSecuritySimpleAuthHandler.accept(request, response, authentication);
            }
    }

}
