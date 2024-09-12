package com.sparta.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.auth.domain.JwtAuthenticationToken;
import com.sparta.auth.exception.jwt.JwtAuthenticationException;
import com.sparta.auth.exception.jwt.JwtNotExistException;
import com.sparta.commons.domain.response.FailedResponseBody;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";
  private final AuthenticationManager authenticationManager;
  private final RequestMatcher requestMatcher = new RequestHeaderRequestMatcher(
      HttpHeaders.AUTHORIZATION);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (!requestMatcher.matches(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String tokenValue = resolveToken(request).orElseThrow(JwtNotExistException::new);
      JwtAuthenticationToken token = new JwtAuthenticationToken(tokenValue); // 인증되지 않은 토큰
      Authentication authentication = this.authenticationManager.authenticate(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    } catch (JwtAuthenticationException e) {
      this.handleServiceException(response, e);
    }
  }

  private Optional<String> resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
      return Optional.of(bearerToken.substring(BEARER_PREFIX.length()));
    }
    return Optional.empty();
  }

  private void handleServiceException(HttpServletResponse response, JwtAuthenticationException e)
      throws IOException {
    response.setStatus(e.getErrorCode().getStatus());
    response.setContentType("application/json;charset=UTF-8");
    ObjectMapper objectMapper = new ObjectMapper();
    String errorResponse = objectMapper.writeValueAsString(
        new FailedResponseBody(e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
    response.getWriter().write(errorResponse);
    response.flushBuffer();
    response.getWriter().close();
  }

}
