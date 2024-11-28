package diy.mqml.datalayer.dto.user;



import diy.mqml.datalayer.persistence.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    protected Long id;
    protected String lanId;
    protected String lastName;
    protected String firstName;

    protected String fullName;
    protected String emailAddress;

    protected String department;
    protected String jobTitle;
    protected LocalDateTime accessDateTime;
    protected boolean photoAvailable;

    public UserDto(User user) {
        this.id = user.getId();
        this.lanId = user.getLanId();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.fullName = user.getFullName();
        this.emailAddress = user.getEmailAddress();
        this.department = user.getDepartment();
        this.jobTitle = user.getJobTitle();
        this.accessDateTime = user.getAccessDateTime();
        this.photoAvailable = user.isPhotoAvailable();
    }

}
