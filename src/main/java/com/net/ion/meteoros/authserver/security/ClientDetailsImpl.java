package com.net.ion.meteoros.authserver.security;

import com.net.ion.meteoros.authserver.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class ClientDetailsImpl implements ClientDetails
{
    @Autowired
    PasswordEncoder encoder;

    private String clientId;
    private Set<String> resourceIds;
    private String clientSecrete;
    private Set<String> scopes;
    private Set<String> grantTypes;
    private Set<String> redirectUris;
    private Integer tokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Set<Authority> authorities;

    public void setClient(Client client)
    {
        this.clientId = client.getClientId();
        this.clientSecrete = client.getSecrete();
        this.tokenValiditySeconds = client.getTvs();
        this.refreshTokenValiditySeconds = client.getRtvs();
        //this.clientSecrete = encoder.encode("1111");

        authorities = new HashSet<>();
        authorities.add(new Authority("ROLE_CLIENT"));
        authorities.add(new Authority("ROLE_TRUSTED_CLIENT"));
        authorities.add(new Authority("USER"));

        if (client.getResourceIds() != null)
        {
            String[] arr = client.getResourceIds().split(",");

            this.resourceIds = new HashSet<>();
            Collections.addAll(this.resourceIds, arr);
        }

        if (client.getScopes() != null)
        {
            String[] arr = client.getScopes().split(",");

            this.scopes = new HashSet<>();
            Collections.addAll(this.scopes, arr);
        }

        if (client.getGrantTypes() != null)
        {
            String[] arr = client.getGrantTypes().split(",");

            this.grantTypes = new HashSet<>();
            Collections.addAll(this.grantTypes, arr);
        }

        if (client.getRedirectUris() != null)
        {
            String[] arr = client.getRedirectUris().split(",");

            this.redirectUris = new HashSet<>();
            Collections.addAll(this.redirectUris, arr);
        }
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientSecrete;
    }

    @Override
    public boolean isScoped() {
        return (scopes != null && scopes.isEmpty() == false);
    }

    @Override
    public Set<String> getScope() {
        return scopes;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return redirectUris;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return (Collection<GrantedAuthority>)(Collection<?>)authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
