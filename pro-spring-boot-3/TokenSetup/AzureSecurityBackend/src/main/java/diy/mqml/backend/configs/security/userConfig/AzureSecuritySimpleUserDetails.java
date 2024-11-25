package diy.mqml.backend.configs.security.userConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


//@Getter
//@Setter
//@Accessors(chain = true)
public class AzureSecuritySimpleUserDetails implements UserDetails, Authentication, CredentialsContainer {

    protected final AzureSecuritySimpleUser user;
    protected List<? extends GrantedAuthority> authorities;
    protected boolean authenticated = false;

    public AzureSecuritySimpleUserDetails(AzureSecuritySimpleUser user,
                                  Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        setAuthorities(authorities);
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getCredentials() {
        return user.getLanId();
    }

    @Override
    public AzureSecuritySimpleUser getDetails() {
        return user;
    }

    @Override
    public String getName() {
        return user.getLanId();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return user.getLanId();
    }

    public AzureSecuritySimpleUserSession getUserSession() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getLanId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        if (authorities != null && !authorities.isEmpty()) {
            this.authorities = List.copyOf(authorities);
        } else {
            this.authorities = List.of();
        }
    }





}
