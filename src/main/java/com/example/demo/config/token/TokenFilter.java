package com.example.demo.config.token;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

public class TokenFilter extends GenericFilter {


    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //check if request to backend has token

        //check for Authorization header
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(authorization)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //check for Bearer type
        if(!authorization.startsWith("Bearer ")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //extract token
        String token = authorization.substring(7); //"Bearer " = 7letters
        //verify token
        DecodedJWT decodedJWT = tokenService.isValid(token);
        if(decodedJWT == null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //get userid
        String principal = decodedJWT.getClaim("principal").asString();
        //get role
        String role = decodedJWT.getClaim("role").asString();
        //create List of role (required by Spring)
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        //authenticate
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, "(protected)", authorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

    }
}
