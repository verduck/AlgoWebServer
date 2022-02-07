package kr.ac.jj.algo.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ROLE_APPLICANT,
    ROLE_USER,
    ROLE_ADMIN_ASSISTANT,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
