package com.sparta.order.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.order.presentation.SecurityContextFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  private final ObjectMapper objectMapper;
  @Bean
  public SecurityContextFilter securityContextFilter() {
    return new SecurityContextFilter(objectMapper);
  }
  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            (sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(securityContextFilter(), UsernamePasswordAuthenticationFilter.class);
        //TODO :: 에러핸들링 등록
    return http.build();
  }

}
