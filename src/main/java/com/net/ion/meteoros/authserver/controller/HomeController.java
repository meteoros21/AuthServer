package com.net.ion.meteoros.authserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.net.ion.meteoros.authserver.entity.User;
import com.net.ion.meteoros.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class HomeController
{
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    UserRepository userRepository;

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

    @RequestMapping(value = "/userInfo")
    public String userInfo(@RequestParam("token") String token) throws Exception
    {
        Authentication authentication = tokenStore.readAuthentication(token);

        User user = userRepository.findOneByUserId(authentication.getName());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }
}
