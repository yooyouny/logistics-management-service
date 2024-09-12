package com.sparta.user.filter;

import com.sparta.commons.domain.jwt.JwtClaim;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public record JwtAuthentication(
    Long userId,
    String username,
    String role
) implements Authentication {

  public static JwtAuthentication create(JwtClaim claims) {
    return new JwtAuthentication(claims.getUserId(), claims.getUsername(), claims.getRole());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(role));
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return JwtClaim.create(userId, username, role);
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

  }

  @Override
  public String getName() {
    return username;
  }
}