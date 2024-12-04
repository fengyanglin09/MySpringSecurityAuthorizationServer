package diy.mqml.actuatorlayer.service;


import diy.mqml.actuatorlayer.actuator.LogEventEndpoint;
import diy.mqml.actuatorlayer.events.UserActivatedEvent;
import diy.mqml.actuatorlayer.model.User;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class UserEventService {

    private final List<User> userRepository;

    private final ApplicationEventPublisher publisher;

    private final LogEventEndpoint logEventsEndpoint;


    public UserEventService(ApplicationEventPublisher publisher, LogEventEndpoint logEventsEndpoint) {
        this.publisher = publisher;
        this.logEventsEndpoint = logEventsEndpoint;

        this.userRepository = new ArrayList<>();

    }

    public Iterable<User> getAllUsers(){
        return this.userRepository;
    }

    public User saveUpdateUser(User user){
        this.userRepository.add(user);

        if (logEventsEndpoint.isEnable())
            this.publisher.publishEvent(new UserActivatedEvent(user.getEmail(), user.isActive()));
        return user;
    }

}
