package diy.mqml.backend.controller.restController.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/user")
    public Map<String, Object> userInfo(@AuthenticationPrincipal OidcUser principal) {



        final Map<String, Object> claims = principal.getClaims();

        final Map<String, Object> objectMap = Map.of(
                "name", claims.get("name"),
                "email", claims.get("preferred_username")
        );

        return objectMap;
    }


//    @GetMapping("/login-authentication")
//    public ResponseEntity<String> toLogin(){
//
//    }
}
