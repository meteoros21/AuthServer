package com.net.ion.meteoros.authserver.security;

import com.net.ion.meteoros.authserver.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails
{
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    private List<Role> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    public void setUser(User user)
    {
        this.username = user.getUserId();
        this.password = user.getUserPwd();

        this.credentialsNonExpired = user.getIsDeleted().equals("N");

        authorities = new ArrayList<Role>();
        Role roleTmp = new Role("ROLE_USER");
        authorities.add(roleTmp);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }
}
