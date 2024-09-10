package com.sparta.gateway.config;

import feign.Logger;
import feign.Logger.Level;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;

public class AuthFeignClientConfig {

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Level.FULL;
  }

  @Bean
  public Decoder feignDecoder() {
    ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;
    return new SpringDecoder(messageConverters);
  }
}
