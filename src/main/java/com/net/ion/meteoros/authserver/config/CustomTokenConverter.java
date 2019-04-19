package com.net.ion.meteoros.authserver.config;

import com.net.ion.meteoros.authserver.entity.User;
import com.net.ion.meteoros.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenConverter extends JwtAccessTokenConverter // implements TokenEnhancer
{
    @Autowired
    UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication)
    {
        String userName = oAuth2Authentication.getUserAuthentication().getName();

        User user = userRepository.findOneByUserId(userName);

        Map<String, Object> additonalUserInfo = new HashMap<>();
        additonalUserInfo.put("userId", user.getUserId());
        additonalUserInfo.put("realName", user.getUserName());

        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(additonalUserInfo);

        return super.enhance(oAuth2AccessToken, oAuth2Authentication);
    }
}
