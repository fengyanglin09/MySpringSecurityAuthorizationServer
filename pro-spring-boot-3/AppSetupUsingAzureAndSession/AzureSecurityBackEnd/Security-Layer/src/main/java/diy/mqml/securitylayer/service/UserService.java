package diy.mqml.securitylayer.service;


import diy.mqml.datalayer.dto.user.AuthUserDto;
import diy.mqml.datalayer.dto.user.UserDto;
import diy.mqml.datalayer.persistence.entity.user.User;
import diy.mqml.datalayer.persistence.entity.user.UserRepository;
import diy.mqml.datalayer.persistence.entity.user.UserRole;
import diy.mqml.securitylayer.configs.security.userConfig.SecuritySimpleUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private static final Comparator<String> STRING_NULL_COMPARATOR = Comparator.nullsFirst(Comparator.naturalOrder());
    private static final Comparator<User> USER_COMPARATOR = Comparator.comparing(User::getLanId, STRING_NULL_COMPARATOR)
            .thenComparing(User::getFullName, STRING_NULL_COMPARATOR)
            .thenComparing(User::getEmailAddress, STRING_NULL_COMPARATOR)
            .thenComparing(User::getDepartment, STRING_NULL_COMPARATOR)
            .thenComparing(User::getJobTitle, STRING_NULL_COMPARATOR);

    private final SecuritySimpleContextService securityContextService;
    private final UserRepository userRepository;

    public UserService(SecuritySimpleContextService securityContextService,
                       UserRepository userRepository) {
        this.securityContextService = securityContextService;
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByLanId(String landId) {
        return userRepository.findByLanId(landId);
    }

    public Optional<UserDto> findSystemUser() {
        return userRepository.findSystemUser()
                .map(UserDto::new);
    }

    @Transactional
    public void noteUserLogin(User user) {
        LocalDateTime currentDate = LocalDateTime.now();
        userRepository.noteUserLogin(user.getId(), currentDate);
        user.setAccessDateTime(currentDate);
        userRepository.save(user);
    }

    public AuthUserDto getCurrentUser() {
        return securityContextService.findUser()
                .flatMap(user -> findUserByLanId(user.getLanId()))
                .map(user -> {
                    List<UserRole> userRoles = mapAuthoritiesToRoles(securityContextService.findAuthorities());
                    return new AuthUserDto(user, new HashSet<>(userRoles));
                })
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<User> getAuditUser(){
        return securityContextService.findUser()
                .flatMap(user -> findUserByLanId(user.getLanId()));
    }

    public Set<UserRole> getAllApplicableRoles(User user) {
        return findUserByLanId(user.getLanId())
                .map(User::getRoles)
                .orElseGet(Set::of);
    }

    public boolean validateRoles(User user, Collection<UserRole> roles) {
        Set<String> roleNames = findUserByLanId(user.getLanId())
                .map(u -> user.getRoles().stream()
                        .map(UserRole::getName)
                        .collect(Collectors.toSet()))
                .orElseGet(Set::of);
        return roles.stream().allMatch(r -> roleNames.contains(r.getName()));
    }

    public List<UserRole> mapAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        if (authorities != null && !authorities.isEmpty()) {
            return authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(authority -> !authority.equals(UserRole.AUTHORIZED_SECURITY_ROLE) &&
                            !authority.equals(UserRole.UNAUTHORIZED_SECURITY_ROLE))
                    .map(authority -> {
                        try {
                            return Optional.of(UserRole.getRole(authority));
                        } catch (Exception ex) {
                            log.warn("Unknown authority {}", authority);
                            return Optional.<UserRole>empty();
                        }
                    })
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        }
        return List.of();
    }

    public List<? extends GrantedAuthority> mapRolesToAuthorities(List<UserRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getSecurityRole())));
        }
        if (!authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(UserRole.AUTHORIZED_SECURITY_ROLE));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.UNAUTHORIZED_SECURITY_ROLE));
        }
        return authorities;
    }

    public List<UserRole> mapRoleNamesToRoles(Collection<String> roleNames) {
        if (roleNames != null) {
            return roleNames.stream()
                    .map(role -> {
                        try {
                            return Optional.of(UserRole.valueOf(role));
                        } catch (Exception ex) {
                            log.warn("Unknown role {}", role);
                            return Optional.<UserRole>empty();
                        }
                    })
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .distinct()
                    .toList();
        }
        return List.of();
    }

    public boolean hasAnyRoles(UserRole... roles) {
        List<? extends GrantedAuthority> authorities = securityContextService.findAuthorities();
        if (!authorities.isEmpty()) {
            for (UserRole role : roles) {
                if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals(role.getAuthority()) || authority.getAuthority().equals(role.getSecurityRole()))) {

                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public void registerUser(SecuritySimpleUser authenticatedUser) {
        User authUser = new User(authenticatedUser.getLanId(), authenticatedUser.getLastName(), authenticatedUser.getFirstName())
                .setFullName(authenticatedUser.getFullName())
                .setEmailAddress(authenticatedUser.getEmailAddress())
                .setDepartment(authenticatedUser.getDepartment())
                .setJobTitle(authenticatedUser.getJobTitle())
                .setPhoto(authenticatedUser.getPhoto())
                .setRoles(new HashSet<>(mapRoleNamesToRoles(authenticatedUser.getRoles())));
        User user = findUserByLanId(authUser.getLanId()).orElse(null);
        if (user == null) {
            log.info("Creating user with lanid {} ({})", authUser.getLanId(), authUser.getFullName());
            user = userRepository.save(authUser);
        } else if (!matches(authUser, user) || !Arrays.equals(user.getPhoto(), authUser.getPhoto())) {
            log.info("Updating user roles with lanid {} ({})", authUser.getLanId(), authUser.getFullName());
            user.setLastName(authUser.getLastName())
                    .setFirstName(authUser.getFirstName())
                    .setFullName(authUser.getFullName())
                    .setEmailAddress(authUser.getEmailAddress())
                    .setDepartment(authUser.getDepartment())
                    .setJobTitle(authUser.getJobTitle())
                    .setPhoto(authUser.getPhoto())
                    .setRoles(authUser.getRoles());
            user = userRepository.save(user);
        }
        noteUserLogin(user);
    }

    private boolean matches(User user1, User user2) {
        return USER_COMPARATOR.compare(user1, user2) == 0 &&
                user1.getRoles().size() == user2.getRoles().size() &&
                user1.getRoles().containsAll(user2.getRoles());
    }

}
