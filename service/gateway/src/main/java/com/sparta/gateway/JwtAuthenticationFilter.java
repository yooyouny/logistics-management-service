package com.sparta.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.commons.domain.jwt.JwtClaim;
import com.sparta.gateway.application.AuthService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter {

  public static final String BEARER_PREFIX = "Bearer ";
  public static final String AUTHORIZATION = "Authorization";
  public static final String X_USER_CLAIMS = "x-user-claims";
  private final AuthService authService;

  public JwtAuthenticationFilter(@Lazy AuthService authService) {
    this.authService = authService;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();

    if (path.startsWith("/api/auth/")) { // 회원가입/로그인이라면 추가 검사없이 넘김.
      return chain.filter(exchange);
    }

    Optional<String> token = this.extractToken(exchange);

    if (token.isPresent()) {
      // TODO. 게이트웨이에서 오류 응답하는 방법 확인 후, 오류 처리 적용할 예정
      JwtClaim jwtClaim = authService.verifyToken(BEARER_PREFIX + token.get());
      if (jwtClaim == null) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }
      this.addUserClaimsToHeaders(exchange, jwtClaim);
    }

    return chain.filter(exchange);
  }

  // request 헤더에 claim 정보를 추가하는 메서드
  private void addUserClaimsToHeaders(ServerWebExchange exchange, JwtClaim jwtClaim) {
    if (jwtClaim != null) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        String jsonClaims = objectMapper.writeValueAsString(jwtClaim);
        ServerHttpRequest request = exchange.getRequest().mutate()
            .header(X_USER_CLAIMS, URLEncoder.encode(jsonClaims, StandardCharsets.UTF_8))
            .build();
        exchange = exchange.mutate().request(request).build();
      } catch (JsonProcessingException e) {
        log.info("JsonProcessingException : {}", e.getMessage());
      }
    }
  }

  private Optional<String> extractToken(ServerWebExchange exchange) {
    String authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
    if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
      return Optional.of(authHeader.substring(BEARER_PREFIX.length()));
    }
    return Optional.empty();
  }
}
