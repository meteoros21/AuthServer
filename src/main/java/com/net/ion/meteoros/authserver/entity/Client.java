package com.net.ion.meteoros.authserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_client")
public class Client
{
    @Id
    private String clientId;
    private String secrete;
    private String resourceIds;
    private String scopes; // read, write, trusted
    private String grantTypes; // password, refresh_token, implicit, authorization_code
    private String authorities;
    private String redirectUris;
    private int tvs;
    private int rtvs;

    public String getClientId() {
        return clientId;
    }

    public String getSecrete() {
        return secrete;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public String getScopes() {
        return scopes;
    }

    public String getGrantTypes() {
        return grantTypes;
    }

    public String getAuthorities() {
        return authorities;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public int getTvs() {
        return tvs;
    }

    public int getRtvs() {
        return rtvs;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setSecrete(String secrete) {
        this.secrete = secrete;
    }

    public void setResourceId(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    public void setTvs(int tvs) {
        this.tvs = tvs;
    }

    public void setRtvs(int rtvs) {
        this.rtvs = rtvs;
    }
}
