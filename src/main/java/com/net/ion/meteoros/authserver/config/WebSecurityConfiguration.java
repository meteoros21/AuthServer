package com.net.ion.meteoros.authserver.config;

import com.net.ion.meteoros.authserver.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    // Defined in AuthorizationServerConfiguration class
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean () throws Exception
    {
        return super.authenticationManagerBean ();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // CSRF
        http.csrf().disable();

        // Log out
        http.logout().permitAll();

        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/status").permitAll()
                .antMatchers("/userInfo").permitAll()
                .anyRequest().authenticated();
    }
}
