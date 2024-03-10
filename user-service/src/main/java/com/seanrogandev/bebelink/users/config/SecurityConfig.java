package com.seanrogandev.bebelink.users.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${app.security.username:me}")
    private String username;

    @Value("${app.security.password:pass}")
    private String password;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable() // Disable CSRF protection for simplicity in testing.
                .authorizeExchange()
                .anyExchange().permitAll() // Permit all requests without authentication.
                .and()
                .httpBasic().disable() // Disable HTTP Basic authentication.
                .formLogin().disable(); // Disable form login authentication if you have it enabled.
        return http.build();
//
//
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//                .csrf().disable()
//                .authorizeExchange()
//                .anyExchange().authenticated()
//                .and()
//                .httpBasic();
//        return http.build();
//    }
//
//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password("{noop}" + "pass") // {noop} for plain text. Consider using password encoding.
//                .roles("USER_PAID")
//                .build();
//        return new MapReactiveUserDetailsService(user);
//    }
    }
}

