package com.algo.algoweb.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ROLE_APPLICANT, ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return null;
    }
}
