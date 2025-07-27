package com.digitaltol.translation.config;


import com.digitaltol.translation.security.JwtFilter;
import com.digitaltol.translation.security.JwtUtil;
import com.digitaltol.translation.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwtUtil, AuthService authService) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(jwtUtil, authService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}