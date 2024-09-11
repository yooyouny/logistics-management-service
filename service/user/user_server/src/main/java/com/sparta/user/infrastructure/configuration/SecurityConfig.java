package com.sparta.user.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.user.SecurityContextFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain httpSecurity(HttpSecurity http, ObjectMapper objectMapper) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .sessionManagement((s) -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // TODO. 질문. 왜 설정했는데, 쿠키생성?
        .rememberMe(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .requestCache(RequestCacheConfigurer::disable)


        .addFilterAfter(new SecurityContextFilter(objectMapper),
            UsernamePasswordAuthenticationFilter.class)
    ;

    return http.build();
  }
}
