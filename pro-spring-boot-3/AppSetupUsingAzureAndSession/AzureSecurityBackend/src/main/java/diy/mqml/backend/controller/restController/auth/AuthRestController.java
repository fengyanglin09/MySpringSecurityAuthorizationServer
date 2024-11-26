package diy.mqml.backend.controller.restController.auth;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-status")
@Slf4j
public class AuthRestController {
    @GetMapping("/session-status")
    public ResponseEntity<String> getSessionStatus() {
        // Check if the user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String
                && authentication.getPrincipal().equals("anonymousUser"))) {
            return ResponseEntity.ok("Authenticated");
        } else {
            log.warn("user is not authenticated!");
            return ResponseEntity.ok("Unauthenticated");
        }
    }



}
