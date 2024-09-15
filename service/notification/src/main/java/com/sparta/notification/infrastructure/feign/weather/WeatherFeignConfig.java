package com.sparta.notification.infrastructure.feign.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherFeignConfig {

  @Bean
  public Decoder decoder(ObjectMapper objectMapper) {
    return new WeatherFeignDecoder(objectMapper);
  }
}
