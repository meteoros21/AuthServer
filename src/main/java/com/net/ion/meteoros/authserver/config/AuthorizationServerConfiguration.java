package com.net.ion.meteoros.authserver.config;

import com.net.ion.meteoros.authserver.entity.Client;
import com.net.ion.meteoros.authserver.repository.ClientRepository;
import com.net.ion.meteoros.authserver.security.ClientDetailsImpl;
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
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.HashMap;
import java.util.Map;

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
    public static String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    @Autowired
    //@Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsServiceImpl clientDetailsService;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        // Method-1
        // 메모리에 대충 클라이언트 정보를 등록한다.
        //clients.inMemory()
        //        .withClient(CLIENT_ID)
        //            .authorizedGrantTypes(GRANT_TYPE_PASSWORD, GRANT_TYPE_CLIENT_CREDENTIALS, GRANT_TYPE_REFRESH_TOKEN, GRANT_TYPE_IMPLICIT)
        //            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER")
        //            .scopes("read", "write", "trust")
        //            .secret(passwordEncoder().encode(CLIENT_SECRETE));

        // Method-2
        // 클라이언트 디테일 서비스를 생성해서 제공한다.
        // 매번 데이터베이스로부터 클라이언트 정보를 읽어 제공한다. 속도나 부하가 걸릴 수도 있다.
        // 이상하게 한번 토큰 발급 시, 여러번 데이터베이스를 호출한다.
        // clients.withClientDetails(clientDetailsService);

        // Method-3
        // 클라이언트를 한꺼번에 조회해서 메모리 맵 형태로 제공한다.
        // 매번 DB 조회를 하지 않아도 되어 빠르고 부하도 없다, 클라이언트의 정보가 변경되면 서버를 다시 시작해야 한다.
        // 물론 다시 클라이언트를 로드하라는 외부 컨트롤러를 이용할 수도 있지만..
        Map<String, ClientDetails> clientStore = new HashMap<>();
        Iterable<Client> clientList = clientRepository.findAll();
        for (Client client : clientList)
        {
            ClientDetailsImpl clientDetails = new ClientDetailsImpl();
            clientDetails.setClient(client);
            clientStore.put(clientDetails.getClientId(), clientDetails);
        }

        InMemoryClientDetailsService inMemoryClientDetailsService = new InMemoryClientDetailsService();
        inMemoryClientDetailsService.setClientDetailsStore(clientStore);
        clients.withClientDetails(inMemoryClientDetailsService);
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
