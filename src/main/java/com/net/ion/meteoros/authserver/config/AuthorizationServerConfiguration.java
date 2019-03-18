package com.net.ion.meteoros.authserver.config;

import com.net.ion.meteoros.authserver.security.ClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter
{
    public static String CLIENT_ID = "test-api1";
    public static String CLIENT_SECRETE = "1111";

    public static String GRANT_TYPE_PASSWORD = "password";
    public static String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    public static String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    public static String GRANT_TYPE_IMPLICIT = "implicit";

    @Autowired
    //@Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsServiceImpl clientDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        clients.inMemory()
                .withClient(CLIENT_ID)
                    .authorizedGrantTypes(GRANT_TYPE_PASSWORD, GRANT_TYPE_AUTHORIZATION_CODE, GRANT_TYPE_REFRESH_TOKEN, GRANT_TYPE_IMPLICIT)
                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER")
                    .scopes("read", "write", "trust")
                    .secret(passwordEncoder().encode(CLIENT_SECRETE));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
    {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
    {
        // 토큰을 얻기 위한 액세스에 제한을 두지 않는다.
        security.tokenKeyAccess("permitAll()");

        // 토큰 검증을 위한 액세스는 인증 받은 리소스 서버만 접근 가능하다.
        security.checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore()
    {
        return new InMemoryTokenStore();
    }
}
