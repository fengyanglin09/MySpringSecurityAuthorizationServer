package diy.mqml.datalayer.service;


import diy.mqml.datalayer.persistence.entity.user.User;
import diy.mqml.datalayer.persistence.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class test {

    private final test2 test2;


    private final UserRepository userRepository;

    public test(diy.mqml.datalayer.service.test2 test2, UserRepository userRepository) {
        this.test2 = test2;
        this.userRepository = userRepository;

        final Optional<User> m1223 = this.userRepository.findByLanId("m1223");

        int i = 0;
    }
}
