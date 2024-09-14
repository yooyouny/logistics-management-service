package com.sparta.hub.presentation;

import com.sparta.commons.domain.jwt.JwtClaim;
import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.hub.application.dto.hub.HubCreateRequest;
import com.sparta.hub.application.dto.hub.HubResponse;
import com.sparta.hub.application.dto.hub.HubSearchCond;
import com.sparta.hub.application.dto.hub.HubUpdateRequest;
import com.sparta.hub.application.service.HubService;
import com.sparta.hub.infrastructure.config.AuthenticationImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hubs")
public class HubController {

  private final HubService hubService;

  @PreAuthorize("isAuthenticated() and hasRole('ROLE_MASTER')")
  @PostMapping
  public ResponseBody<HubResponse> createHub(@RequestBody HubCreateRequest hubCreateRequest) {
    HubResponse response = hubService.createHub(hubCreateRequest);
    return new SuccessResponseBody<>(response);
  }

  @PreAuthorize("isAuthenticated() and hasRole('MASTER')")
  @PutMapping("/{hubId}")
  public ResponseBody<HubResponse> updateHub(@Valid @RequestBody HubUpdateRequest requestDto,
      @PathVariable UUID hubId) {
    HubResponse hubResponse = hubService.updateHub(requestDto, hubId);
    return new SuccessResponseBody<>(hubResponse);
  }

  @PreAuthorize("isAuthenticated() and hasRole('ROLE_MASTER')")
  @DeleteMapping("/{hubId}")
  public ResponseBody<UUID> deleteHub(
     @PathVariable UUID hubId) {
    hubService.deleteHub(hubId);
    return new SuccessResponseBody<>(hubId);
  }

  @GetMapping("/{hubId}")
  public ResponseBody<HubResponse> getSingleHub(@PathVariable UUID hubId) {
    HubResponse responseHub = hubService.getSingleHub(hubId);
    return new SuccessResponseBody<>(responseHub);
  }

  @GetMapping
  public ResponseBody<Page<HubResponse>> getAllHubs(Pageable pageable, HubSearchCond cond) {
    Page<HubResponse> allHub = hubService.getAllHub(pageable, cond);
    return new SuccessResponseBody<>(allHub);
  }


}
