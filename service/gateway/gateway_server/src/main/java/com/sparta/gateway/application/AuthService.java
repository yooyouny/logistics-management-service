package com.sparta.gateway.application;

import com.sparta.commons.domain.jwt.JwtClaim;

public interface AuthService {

  JwtClaim verifyToken(String token);
}