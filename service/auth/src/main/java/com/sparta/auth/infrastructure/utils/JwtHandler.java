package com.sparta.auth.infrastructure.utils;

import com.sparta.commons.domain.jwt.JwtClaim;
import com.sparta.auth.infrastructure.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtHandler {

  public static final String USER_ID = "USER_ID";
  public static final String USER_NAME = "USER_NAME";
  public static final String USER_ROLE = "USER_ROLE";
  private static final long MILLI_SECOND = 1000L;

  private final SecretKey secretKey;
  private final JwtProperties jwtProperties;

  public JwtHandler(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
    secretKey =
        new SecretKeySpec(
            jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  // access token 생성
  public String createToken(JwtClaim jwtClaim) {
    Map<String, Object> tokenClaims = this.createClaims(jwtClaim);
    Date now = new Date(System.currentTimeMillis());
    long accessTokenExpireIn = jwtProperties.getAccessTokenExpireIn();

    return Jwts.builder()
        .claims(tokenClaims)
        .issuedAt(now)
        .expiration(new Date(now.getTime() + accessTokenExpireIn * MILLI_SECOND))
        .signWith(secretKey)
        .compact();
  }

  // claim 생성
  public Map<String, Object> createClaims(JwtClaim jwtClaim) {
    return Map.of(
        USER_ID, jwtClaim.getUserId(),
        USER_NAME, jwtClaim.getUsername(),
        USER_ROLE, jwtClaim.getRole());
  }

  // 필터에서 토큰의 상태를 검증하기 위한 메서드 exception은 사용하는 곳에서 처리
  public JwtClaim parseToken(String token) {
    Claims claims =
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

    return this.convert(claims);
  }

  public JwtClaim convert(Claims claims) {
    return JwtClaim.create(
        claims.get(USER_ID, Long.class),
        claims.get(USER_NAME, String.class),
        claims.get(USER_ROLE, String.class));
  }
}
