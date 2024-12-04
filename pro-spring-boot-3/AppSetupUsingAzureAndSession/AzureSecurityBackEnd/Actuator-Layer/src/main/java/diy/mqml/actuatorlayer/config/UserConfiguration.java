package diy.mqml.actuatorlayer.config;


import diy.mqml.actuatorlayer.model.User;
import diy.mqml.actuatorlayer.model.UserRole;
import diy.mqml.actuatorlayer.service.UserEventService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class UserConfiguration {

    @Bean
    CommandLineRunner init(UserEventService userEventService){
        return args -> {
            userEventService.saveUpdateUser(new User("ximena@email.com","Ximena","https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar","aw2s0me", Arrays.asList(UserRole.USER),true));
            userEventService.saveUpdateUser(new User("norma@email.com","Norma" ,"https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar", "aw2s0me", Arrays.asList(UserRole.USER, UserRole.ADMIN),false));
            userEventService.saveUpdateUser(new User("dummy@email.com","Dummy" ,"https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar", "aw2s0me", Arrays.asList(UserRole.USER, UserRole.ADMIN),false));


        };
    }

}
