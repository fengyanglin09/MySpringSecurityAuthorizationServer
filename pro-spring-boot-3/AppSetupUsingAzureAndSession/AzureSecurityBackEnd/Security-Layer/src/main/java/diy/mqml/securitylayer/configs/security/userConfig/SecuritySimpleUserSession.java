package diy.mqml.securitylayer.configs.security.userConfig;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;


@Builder
@Getter
//@NoArgsConstructor
public class SecuritySimpleUserSession {
    private final String id;
    private final LocalDateTime authenticatedDateTime;
    private final LocalDateTime issuedDateTime;
    private final LocalDateTime expiredDateTime;


    @Override
    public String toString() {
        return "AzureSecuritySimpleUserSession{" +
                "id='" + id + '\'' +
                ", authenticatedDateTime=" + authenticatedDateTime +
                ", issuedDateTime=" + issuedDateTime +
                ", expiredDateTime=" + expiredDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecuritySimpleUserSession that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAuthenticatedDateTime(), that.getAuthenticatedDateTime()) && Objects.equals(getIssuedDateTime(), that.getIssuedDateTime()) && Objects.equals(getExpiredDateTime(), that.getExpiredDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthenticatedDateTime(), getIssuedDateTime(), getExpiredDateTime());
    }
}
