package com.lb.employeeleave.security;

import com.lb.employeeleave.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@EnableAuthorizationServer
@Configuration
public class SecurityOAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final AppConfig appConfig;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityOAuth2Configuration(AuthenticationManager authenticationManager, AppConfig appConfig, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.appConfig = appConfig;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("demo-client")
                .secret(passwordEncoder.encode("demo-secret"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write")
                .accessTokenValiditySeconds(86400).
                refreshTokenValiditySeconds(-1);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(appConfig.tokenStore()); // Persist the tokens in the database
    }
}
