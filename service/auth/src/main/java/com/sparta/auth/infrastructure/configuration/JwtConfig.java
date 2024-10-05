package com.sparta.auth.infrastructure.configuration;

import com.sparta.auth.infrastructure.properties.JwtProperties;
import com.sparta.auth.infrastructure.utils.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

  @Bean
  public JwtHandler jwtHandler(JwtProperties jwtProperties) {
    return new JwtHandler(jwtProperties);
  }
}
