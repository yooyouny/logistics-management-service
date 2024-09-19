package com.sparta.hub.presentation;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.hub.application.dto.interhub.InterHubCreateRequest;
import com.sparta.hub.application.dto.interhub.InterHubSearchCond;
import com.sparta.hub.application.dto.interhub.InterHubUpdateRequest;
import com.sparta.hub.application.service.InterHubService;
import com.sparta.hub.dto.InterHubResponse;
import com.sparta.hub.infrastructure.config.AuthenticationImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inter-hubs")
@Slf4j
public class InterHubController {

  private final InterHubService interHubService;

  @PreAuthorize("isAuthenticated() and hasRole('ROLE_MASTER')")
  @PostMapping
  public ResponseBody<List<InterHubResponse>> createInterHubRoute(
      @Valid @RequestBody InterHubCreateRequest requestDto) {
    List<InterHubResponse> route = interHubService.createRoute(requestDto);
    return new SuccessResponseBody<>(route);
  }

  @PreAuthorize("hasRole('ROLE_MASTER')")
  @PutMapping("/{interHubId}")
  public ResponseBody<InterHubResponse> updateInterHubRoute(
      @Valid @RequestBody InterHubUpdateRequest requestDto, @PathVariable UUID interHubId) {
    InterHubResponse interHubResponse = interHubService.updateRoute(requestDto, interHubId);
    return new SuccessResponseBody<>(interHubResponse);
  }

  @PreAuthorize("isAuthenticated() and hasRole('ROLE_MASTER')")
  @DeleteMapping("/{interHubId}")
  public ResponseBody<UUID> deleteInterHubRoute(@PathVariable UUID interHubId) {
    AuthenticationImpl authentication = (AuthenticationImpl) SecurityContextHolder.getContext()
        .getAuthentication();
    String username = authentication.getName();
    interHubService.delete(interHubId, username);
    return new SuccessResponseBody<>(interHubId);
  }

  @GetMapping("/{interHubId}")
  public ResponseBody<InterHubResponse> getInterHubRoute(@PathVariable UUID interHubId) {
    InterHubResponse response = interHubService.getOneHubRoute(interHubId);
    return new SuccessResponseBody<>(response);
  }

  @GetMapping
  public ResponseBody<Page<InterHubResponse>> getAllInterHubRoutes(@ModelAttribute InterHubSearchCond cond,
      Pageable pageable) {
    Page<InterHubResponse> allHubRoute = interHubService.getAllHubRoute(cond, pageable);
    return new SuccessResponseBody<>(allHubRoute);
  }
}
