package diy.mqml.backend.controller.thymeleaf;


import diy.mqml.backend.configs.appProperty.LandingPagePropConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPage {

    // Inject the URL from application.yml

    private final LandingPagePropConfig landingPagePropConfig;


    public LandingPage(LandingPagePropConfig landingPagePropConfig) {
        this.landingPagePropConfig = landingPagePropConfig;
    }

    @GetMapping("/welcome-page")
    public String showPage(Model model) {
        // Add the external link to the model
        model.addAttribute("externalLink", landingPagePropConfig.getUrl());
        return "welcome-page";
    }

}
