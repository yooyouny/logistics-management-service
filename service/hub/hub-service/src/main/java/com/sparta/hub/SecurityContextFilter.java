package com.sparta.hub;

import static com.sparta.gateway.domain.GatewayConstant.X_USER_CLAIMS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.commons.domain.jwt.JwtClaim;
import com.sparta.hub.infrastructure.config.AuthenticationImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class SecurityContextFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String userClaimsHeader = request.getHeader(X_USER_CLAIMS);

    if (userClaimsHeader != null) {
      try {
        String decodedClaims = URLDecoder.decode(userClaimsHeader, StandardCharsets.UTF_8);
        JwtClaim jwtClaim = objectMapper.readValue(decodedClaims, JwtClaim.class);
        SecurityContextHolder.getContext().setAuthentication(AuthenticationImpl.create(jwtClaim));
      } catch (JsonProcessingException e) {
        log.error("Failed to parse X-User-Claims header", e);
      }
    }

    filterChain.doFilter(request, response);
  }
}
