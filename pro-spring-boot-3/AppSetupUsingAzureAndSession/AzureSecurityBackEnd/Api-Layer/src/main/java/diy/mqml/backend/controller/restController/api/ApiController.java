package diy.mqml.backend.controller.restController.api;

import diy.mqml.securitylayer.configs.security.userConfig.SecuritySimpleUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/user")
    public Map<String, Object> userInfo(
            @AuthenticationPrincipal SecuritySimpleUser simpleUser
    ) {


        final Map<String, Object> objectMap = Map.of(
                "name", simpleUser.getFullName(),
                "email", simpleUser.getEmailAddress()
        );

        return objectMap;
    }


}
