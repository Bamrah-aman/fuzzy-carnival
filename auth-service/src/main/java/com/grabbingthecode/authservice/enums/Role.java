package com.grabbingthecode.authservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.grabbingthecode.authservice.enums.Permissions.*;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(Set.of(ADMIN_READ, ADMIN_UPDATE, ADMIN_CREATE, ADMIN_DELETE, MANAGER_CREATE,
            MANAGER_UPDATE, MANAGER_DELETE, MANAGER_READ)),
    MANAGER(Set.of(MANAGER_CREATE, MANAGER_UPDATE, MANAGER_DELETE, MANAGER_READ));

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = new java.util.ArrayList<>(getPermissions().
                stream().map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}
