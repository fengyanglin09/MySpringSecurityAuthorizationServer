package diy.mqml.datalayer.dto.user;


import diy.mqml.datalayer.persistence.entity.user.UserRole;
import diy.mqml.datalayer.persistence.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.Objects;
import java.util.Set;

@Accessors(chain = true)
@Getter
@Setter
public class AuthUserDto extends UserDto {

    private Set<UserRole> roles;
    private Set<UserRole> originalRoles;

    public AuthUserDto(User user, Set<UserRole> roles) {
        super(user);
        this.roles = roles;
        this.originalRoles = user.getRoles();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthUserDto that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(roles, that.roles) && Objects.equals(originalRoles, that.originalRoles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roles, originalRoles);
    }

    @Override
    public String toString() {
        return "AuthUserDto{" +
                "  id=" + id +
                ", lanId='" + lanId + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", department='" + department + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
//                ", createdOn=" + createdOn +
//                ", updatedOn=" + updatedOn +
                ", lastLoginOn=" + accessDateTime +
                ", roles=" + roles +
                ", originalRoles=" + originalRoles +
                '}';
    }
}
