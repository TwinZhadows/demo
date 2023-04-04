package com.example.demo.config;

import org.hibernate.StatelessSession;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
<<<<<<< Updated upstream

    private final String[] PUBLIC = {
            "/actuator/**", "/test/register", "/test/login", "/socket/**"
    };

=======
    private final String[] PUBLIC = {
            "/actuator/**", "/test/register", "/test/login", "/socket/**"
    };
>>>>>>> Stashed changes
    private final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Bean PasswordEncoder passwordEncoder() { //@Bean is collection of objects
        return new BCryptPasswordEncoder();
    }
        @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
<<<<<<< Updated upstream

        .and().authorizeRequests().antMatchers("/test/register", "/test/login").anonymous().
                anyRequest().authenticated()
=======
>>>>>>> Stashed changes
                .and().authorizeRequests().antMatchers(PUBLIC).anonymous()
                .anyRequest().authenticated()
                .and().apply(new TokenConfigurer(tokenService));

        //stateless
        //allow register and login to be accessed without logging in
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
<<<<<<< Updated upstream
        config.addAllowedOrigin("http://localhost:4200"); //allow localhost:4200 to access
=======
        config.addAllowedOrigin("http://localhost:4200");
>>>>>>> Stashed changes
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
<<<<<<< Updated upstream

        return new CorsFilter(source);

=======
        return new CorsFilter(source);
>>>>>>> Stashed changes
    }
}

