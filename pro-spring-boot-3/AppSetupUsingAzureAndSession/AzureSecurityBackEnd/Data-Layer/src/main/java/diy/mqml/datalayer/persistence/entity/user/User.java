package diy.mqml.datalayer.persistence.entity.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A user of the application.  Define what their roles are and who they are.
 */
@Entity
@Table(schema = "public", name = "app_user",
        uniqueConstraints = @UniqueConstraint(name = "unique_user_lanid", columnNames = "lanId")
)
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer version = 0;

    @NotNull
    private String lanId;

    private LocalDateTime accessDateTime = LocalDateTime.now();

    private String lastName;

    private String firstName;

    private String fullName;

    @Email
    private String emailAddress;

    private String department;

    private String jobTitle;

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "BLOB")//"BLOB is the type for storing binary data in MySQL or H2"
    private byte[] photo;

    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(
            schema = "public",
            name = "app_user_role",
            joinColumns = @JoinColumn(name = "AppUser_id"),
            uniqueConstraints = {@UniqueConstraint(name = "unique_userrole", columnNames = {"AppUser_id", "role"})})
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();

    public User(String fullName) {
        this.fullName = fullName;
    }

    public User(String lanId, String lastname, String firstname) {
        Objects.requireNonNull(lanId);
        Objects.requireNonNull(lastname);
        Objects.requireNonNull(firstname);

        this.lanId = lanId;
        this.lastName = lastname;
        this.firstName = firstname;
        this.fullName = lastname + ", " + firstname;
    }

    public boolean hasRole(UserRole role) {
        return this.roles.contains(role);
    }

    public boolean hasAnyRole(UserRole... roles) {
        return Arrays.stream(roles)
                .anyMatch(this::hasRole);
    }

    public String getDisplayname() {
        if (lanId == null) {
            return getFullName();
        } else {
            return String.format("%s (%s)", getFullName(), lanId);
        }
    }

    public String toString() {
        return "UserEntity{id=" + id +
                ", lanId=" + lanId +
                ", lastname=" + lastName +
                ", firstname=" + firstName +
                ", fullname=" + fullName +
                ", emailAddress=" + emailAddress +
                ", department=" + department +
                ", jobTitle=" + jobTitle +
                '}';
    }

    public boolean isPhotoAvailable() {
        return this.photo != null && this.photo.length > 0;
    }
}
