package diy.mqml.securitylayer.configs.security.userConfig;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Builder
@Getter
@Accessors(chain = true)
public class SecuritySimpleUser implements Serializable {

    private final String id;
    private final String lanId;
    private final String personId;
    private final String systemId;
    private final String fullName;
    private final String lastName;
    private final String firstName;
    private final String department;
    private final String jobTitle;
    private final String workSiteState;
    private final String workSiteCity;
    private final String mailLocation;
    private final String emailAddress;
    private final List<String> roles;
    private final byte[] photo;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecuritySimpleUser that)) return false;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.lanId, that.lanId) &&
                Objects.equals(this.personId, that.personId) &&
                Objects.equals(this.systemId, that.systemId) &&
                Objects.equals(this.fullName, that.fullName) &&
                Objects.equals(this.lastName, that.lastName) &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.department, that.department) &&
                Objects.equals(this.jobTitle, that.jobTitle) &&
                Objects.equals(this.workSiteState, that.workSiteState) &&
                Objects.equals(this.workSiteCity, that.workSiteCity) &&
                Objects.equals(this.mailLocation, that.mailLocation) &&
                Objects.equals(this.emailAddress, that.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lanId, personId, systemId, fullName, lastName, firstName, department, jobTitle, workSiteState, workSiteCity, mailLocation, emailAddress);
    }

    @Override
    public String toString() {
        return "CadSecurityUserImpl{id=" + id +
                ", lanId=" + lanId +
                ", personId=" + personId +
                ", systemId=" + systemId +
                ", fullName=" + fullName +
                ", lastName=" + lastName +
                ", firstName=" + firstName +
                ", department=" + department +
                ", jobTitle=" + jobTitle +
                ", workSiteState=" + workSiteState +
                ", workSiteCity=" + workSiteCity +
                ", mailLocation=" + mailLocation +
                ", emailAddress=" + emailAddress +
                ", roles=" + String.join(", ", roles) +
                "}";
    }

}
