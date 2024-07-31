package com.challenge.operations.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.challenge.operations.auth.filters.JWTAuthenticationFilter;
import com.challenge.operations.auth.filters.JwtValidationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Este bean filtra todas las llamadas que se hagan a la URL  que se encuentra en su configuracion
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(authRules -> authRules
                .requestMatchers(HttpMethod.GET, "/operation", "/users", "/records")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/operation", "/users", "/records")
                .permitAll()
                .requestMatchers(HttpMethod.PUT,"/operation", "/users", "/records")
                .permitAll()
                .requestMatchers(HttpMethod.DELETE, "/operation", "/users","/records")
                .permitAll()
                .anyRequest()
                .authenticated())
                .addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager())) // filtro para login
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager())) // filtro para login
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
