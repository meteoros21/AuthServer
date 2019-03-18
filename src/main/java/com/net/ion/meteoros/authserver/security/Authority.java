package com.net.ion.meteoros.authserver.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority
{
    public String authority;

    public Authority(String authority)
    {
        this.authority = authority;
    }

    public void setAuthority(String authority)
    {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
