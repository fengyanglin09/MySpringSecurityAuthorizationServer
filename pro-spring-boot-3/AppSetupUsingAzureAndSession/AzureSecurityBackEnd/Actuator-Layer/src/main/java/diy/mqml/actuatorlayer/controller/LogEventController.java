package diy.mqml.actuatorlayer.controller;


import diy.mqml.actuatorlayer.actuator.LogEventEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/actuator/api/log-event")
@RequiredArgsConstructor
public class LogEventController {

    private final LogEventEndpoint logEventEndpoint;



    @GetMapping("/turn-off")
    public void turnOffLogEventActuator(){

        this.logEventEndpoint.eventConfig(false, null, null);

    }

    @GetMapping("/turn-on")
    public void turnONLogEventActuator(){

        this.logEventEndpoint.eventConfig(true, null, null);

    }


}
