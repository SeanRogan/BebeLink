package com.seanrogandev.bebelink.users.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.seanrogandev.bebelink.users.model.Permission.*;

@RequiredArgsConstructor
public enum Role {

    ROLE_USER_FREE(Set.of(
            USER_FREE_READ,
            USER_FREE_UPDATE,
            USER_FREE_DELETE,
            USER_FREE_CREATE)),
    ROLE_USER_PAID(Set.of(
            USER_PAID_READ,
            USER_PAID_UPDATE,
            USER_PAID_DELETE,
            USER_PAID_CREATE)),
    ROLE_ADMIN(Set.of(ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE,
            USER_PAID_READ,
            USER_PAID_UPDATE,
            USER_PAID_DELETE,
            USER_PAID_CREATE));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}

