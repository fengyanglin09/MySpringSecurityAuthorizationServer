package diy.mqml.backend.configs.appProperty;


import diy.mqml.customstarterlayer.annotations.MyRetroAudit;
import diy.mqml.customstarterlayer.annotations.MyRetroAuditOutputFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app-landing-page")
@Getter
@Setter
@Accessors(chain = true)
public class LandingPagePropConfig {
    private String name;
    private String description;
    private String url;
}
