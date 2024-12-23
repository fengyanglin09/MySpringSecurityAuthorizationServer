package diy.mqml.datalayer.dto.user;


import diy.mqml.datalayer.persistence.entity.user.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class UserRoleDto {

    private String name;
    private String title;
    private String description;

    public UserRoleDto() {
    }

    public UserRoleDto(UserRole role) {
        this.name = role.name();
        this.title = role.getDisplayName();
        this.description = role.getDescription();
    }

    @Override
    public String toString() {
        return this.title;
    }
}
