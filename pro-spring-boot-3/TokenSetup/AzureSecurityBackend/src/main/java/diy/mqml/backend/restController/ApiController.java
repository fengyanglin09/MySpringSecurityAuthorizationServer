package diy.mqml.backend.restController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiController {

    @GetMapping("/api/user")
    public Map<String, Object> userInfo(@AuthenticationPrincipal OidcUser principal) {
        return Map.of(
                "name", principal.getName(),
                "email", principal.getEmail(),
                "claims", principal.getClaims()
        );
    }
}
