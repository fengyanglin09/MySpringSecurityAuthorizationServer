package diy.mqml.actuatorlayer.actuator;


import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import org.springframework.lang.Nullable;


@Component
@Endpoint(id="event-config")
public class LogEventEndpoint {

    private final LogEventConfig config;

    public LogEventEndpoint() {
        this.config = new LogEventConfig();
    }

    @ReadOperation
    public LogEventConfig config() {
        return config;
    }

    @WriteOperation
    public void eventConfig(@Nullable Boolean enabled,@Nullable String prefix, @Nullable String postfix) {
        if (enabled != null)
            this.config.setEnabled(enabled);
        if (prefix != null)
            this.config.setPrefix(prefix);
        if (postfix != null)
            this.config.setPostfix(postfix);
    }


    public Boolean isEnable() {
        return config.getEnabled();
    }

}
