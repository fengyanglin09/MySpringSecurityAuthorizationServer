package diy.mqml.actuatorlayer.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;


@AllArgsConstructor
@NoArgsConstructor
@Data
//@Entity(name="PEOPLE")
public class User {




    private String email;


    private String name;

    private String gravatarUrl;


    private String password;

    private Collection<UserRole> userRole = Collections.emptyList();

    private boolean active;
}
