package com.ayprojects.helpinghands.security;

import com.ayprojects.helpinghands.models.DhAuthorization;
import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class UserDetailsDecorator implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final String ROLES_PREFIX = "ROLE_";

    private DhUser user;
//    private DhAuthorization user;

    public UserDetailsDecorator(DhUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] roles = user.getRoles();

        if (roles == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(roles).map(role -> (GrantedAuthority) () -> ROLES_PREFIX + role)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getMobileNumber();
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
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public DhUser getUser() {
        return user;
    }
}