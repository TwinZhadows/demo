package com.example.demo.config.token;

import com.example.demo.service.TokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenService tokenService;

    public TokenConfigurer(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        TokenFilter filter = new TokenFilter(tokenService);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
