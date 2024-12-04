package diy.mqml.actuatorlayer.actuator;


import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class LogEventConfig {

    private Boolean enabled = false;

    private String prefix = ">> ";

    private String postfix = " <<";
}
