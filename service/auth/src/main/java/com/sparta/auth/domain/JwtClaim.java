package com.sparta.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtClaim {

  private Long userId;
  private String username;
  private String role;

  public static JwtClaim create(Long userId, String username, String role) {
    return new JwtClaim(userId, username, role);
  }
}
