package diy.mqml.backend.controller.restController.api;

import diy.mqml.backend.configs.security.authenticationConfig.AzureSecurityOIDCUserDetails;
import diy.mqml.backend.configs.security.userConfig.SecuritySimpleUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/user")
    public Map<String, Object> userInfo(
            @AuthenticationPrincipal AzureSecurityOIDCUserDetails userDetails
    ) {


        final SecuritySimpleUser simpleUser = userDetails.getDetails();


        final Map<String, Object> objectMap = Map.of(
                "name", simpleUser.getFullName(),
                "email", simpleUser.getEmailAddress()
        );

        return objectMap;
    }


}
