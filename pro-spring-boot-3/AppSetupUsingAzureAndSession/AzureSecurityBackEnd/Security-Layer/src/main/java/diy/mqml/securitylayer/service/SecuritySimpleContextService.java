package diy.mqml.securitylayer.service;

import diy.mqml.securitylayer.configs.security.userConfig.SecuritySimpleUser;
import diy.mqml.securitylayer.configs.security.userConfig.SecuritySimpleUserDetails;
import diy.mqml.securitylayer.configs.security.userConfig.SecuritySimpleUserSession;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
@Service
@Slf4j
public class SecuritySimpleContextService {

    private final SessionRegistry sessionRegistry = new SessionRegistryImpl();

    public SecuritySimpleContextService() {
    }

    public Optional<SecuritySimpleUserDetails> findAuthentication() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object var3 = authentication.getPrincipal();
                if (var3 instanceof SecuritySimpleUserDetails securityAuthentication) {
                    return Optional.of(securityAuthentication);
                }
            }
        } catch (Exception var4) {
            log.error("Error retrieving security context authentication", var4);
        }

        return Optional.empty();
    }

    public List<? extends GrantedAuthority> findAuthorities() {
        return this.findAuthentication().map(SecuritySimpleUserDetails::getAuthorities).orElseGet(List::of);
    }

    public Optional<SecuritySimpleUser> findUser() {
        return this.findAuthentication().map(SecuritySimpleUserDetails::getDetails);
    }

    public Optional<SecuritySimpleUserSession> findUserSession() {
        return this.findAuthentication().map(SecuritySimpleUserDetails::getUserSession);
    }

    public void setAuthentication(SecuritySimpleUserDetails authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.findAuthentication().ifPresent((authentication) -> {
            authentication.setAuthorities(authorities);
        });
    }

}
