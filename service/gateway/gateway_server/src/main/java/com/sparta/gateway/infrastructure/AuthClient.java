package com.sparta.gateway.infrastructure;

import com.sparta.commons.domain.jwt.JwtClaim;
import com.sparta.gateway.application.AuthService;
import com.sparta.gateway.config.AuthFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", configuration = AuthFeignClientConfig.class)
public interface AuthClient extends AuthService {

  @GetMapping("/internal/auth/verify")
  JwtClaim verifyToken(@RequestHeader("Authorization") String token);
}