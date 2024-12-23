package diy.mqml.securitylayer.configs.security.userConfig;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


//@Getter
//@Setter
//@Accessors(chain = true)
public class SecuritySimpleUserDetails implements UserDetails, Authentication, CredentialsContainer {

    protected final SecuritySimpleUser user;
    protected List<? extends GrantedAuthority> authorities;
    protected boolean authenticated = false;

    public SecuritySimpleUserDetails(SecuritySimpleUser user,
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
    public SecuritySimpleUser getDetails() {
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

    public SecuritySimpleUserSession getUserSession() {
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
