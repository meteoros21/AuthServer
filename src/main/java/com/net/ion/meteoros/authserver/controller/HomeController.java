package com.net.ion.meteoros.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController
{
    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String root()
    {
        return "Hello World";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestHeader("Authorization") String authorization)
    {
        if (authorization != null && authorization.length() > 7)
        {
            String token = authorization.replace("Bearer", "").trim();

            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            tokenStore.removeAccessToken(accessToken);
        }

    }
}
