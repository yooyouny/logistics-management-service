package com.sparta.delivery.presentation;

import static com.sparta.gateway.domain.GatewayConstant.X_USER_CLAIMS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.commons.domain.jwt.JwtClaim;
import com.sparta.delivery.infrastructure.configuration.AuthenticationImpl;
import com.sparta.delivery.presentation.exception.DeliveryErrorCode;
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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "SecurityContextFilter")
@RequiredArgsConstructor
public class SecurityContextFilter extends OncePerRequestFilter {
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String userSignature = request.getHeader(X_USER_CLAIMS);
    try {
      if (StringUtils.hasText(userSignature)) {
        String decodedClaims = URLDecoder.decode(userSignature, StandardCharsets.UTF_8);
        JwtClaim jwtClaim = objectMapper.readValue(decodedClaims, JwtClaim.class);
        SecurityContextHolder.getContext().setAuthentication(AuthenticationImpl.create(jwtClaim));
      }
    } catch (Exception e) {
      throw new BusinessException(DeliveryErrorCode.NO_AUTH);
    }

    log.info("Save user signature in DeliveryServer");

    filterChain.doFilter(request, response);
  }
}
