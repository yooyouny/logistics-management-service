package com.sparta.auth.application;

import com.sparta.auth.domain.JwtAuthentication;
import com.sparta.auth.domain.JwtAuthenticationToken;
import com.sparta.auth.exception.jwt.JwtTokenExpiredException;
import com.sparta.auth.exception.jwt.JwtTokenInvalidException;
import com.sparta.auth.infrastructure.utils.JwtHandler;
import com.sparta.commons.domain.jwt.JwtClaim;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider implements AuthenticationProvider {

  private final JwtHandler jwtHandler;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
    String tokenValue = jwtAuthenticationToken.token();
    if (tokenValue == null) {
      return null;
    }

    try {
      JwtClaim claims = jwtHandler.parseToken(tokenValue);
      return new JwtAuthentication(claims);
    } catch (ExpiredJwtException e) {
      throw new JwtTokenExpiredException(e);
    } catch (Exception e) {
      throw new JwtTokenInvalidException(e);
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
