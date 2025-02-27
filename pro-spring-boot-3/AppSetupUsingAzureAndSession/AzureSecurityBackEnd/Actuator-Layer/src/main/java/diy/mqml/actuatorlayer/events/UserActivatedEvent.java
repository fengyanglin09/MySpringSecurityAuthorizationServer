package diy.mqml.actuatorlayer.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserActivatedEvent {

    private String email;

    private boolean active;
}
