package diy.mqml.actuatorlayer.actuator;


import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;


/**
 *
 * notice that the EventsHealthIndicator class is marked as @Component,
 * so it will be picked up by the Spring Boot and Actuator auto-configuration,
 * and this class will be part of the HealthContributorRegistry as an events component,
 * which is a naming convention.
 *
 * So, in other words, your custom health indicator must end with HealthIndicator.
 * The syntax is <Name>HealthIndicator, and because the name is EventsHealthIndicator, it will be registered as events.
 *
 * */


@AllArgsConstructor
@Component
public class EventsHealthIndicator implements HealthIndicator {

    private LogEventEndpoint logEventEndpoint;

    @Override
    public Health health() {
        if (check() )
            return Health.up().build();
        else
            // Custom Status
            return Health.status(new Status("EVENTS-DOWN","Events are turned off!")).build();

            // Status
            //return Health.status(Status.DOWN).build();
    }

    private boolean check(){
        return this.logEventEndpoint.isEnable();
    }
}
