package com.net.ion.meteoros.authserver.security;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    private String roleName;

    public Role() {
        this.roleName = "ROLE_USER";
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return roleName;
    }
}
