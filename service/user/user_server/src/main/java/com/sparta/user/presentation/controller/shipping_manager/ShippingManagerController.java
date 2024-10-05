package com.sparta.user.presentation.controller.shipping_manager;

import com.sparta.commons.domain.jwt.JwtClaim;
import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.user.application.dto.SippingManagerInfo;
import com.sparta.user.application.service.shipping_manager.ShippingManagerService;
import com.sparta.user.presentation.request.CreateShippingManagerRequest;
import com.sparta.user.presentation.request.UpdateShippingManagerRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/shipping-managers")
public class ShippingManagerController {

  private final ShippingManagerService shippingManagerService;

  @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_MASTER','ROLE_HUB_MANAGER')")
  @PostMapping("/{userId}")
  public ResponseEntity<ResponseBody<Void>> saveShippingManager(
      @PathVariable Long userId, @RequestBody @Valid CreateShippingManagerRequest request) {
    shippingManagerService.saveShippingManager(
        userId, request.slackId(), request.type(), request.hubId());
    return ResponseEntity.ok(new SuccessResponseBody<>());
  }

  @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_MASTER','ROLE_HUB_MANAGER')")
  @PutMapping("/{shippingManagerId}")
  public ResponseEntity<ResponseBody<Void>> updateShippingManager(
      @PathVariable UUID shippingManagerId,
      @RequestBody @Valid UpdateShippingManagerRequest request) {
    shippingManagerService.updateShippingManager(
        shippingManagerId, request.slackId(), request.type(), request.hubId());
    return ResponseEntity.ok(new SuccessResponseBody<>());
  }

  @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_MASTER','ROLE_HUB_MANAGER')")
  @GetMapping("/{shippingManagerId}")
  public ResponseEntity<ResponseBody<SippingManagerInfo>> getShippingManagerInfo(
      @PathVariable UUID shippingManagerId) {
    return ResponseEntity.ok(
        new SuccessResponseBody<>(
            shippingManagerService.getShippingManagerInfo(shippingManagerId)));
  }

  @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_MASTER','ROLE_HUB_MANAGER')")
  @GetMapping
  public ResponseEntity<ResponseBody<Page<SippingManagerInfo>>> getShippingManagerInfos(
      @AuthenticationPrincipal JwtClaim jwtClaim,
      @RequestParam(required = false) String keyword,
      Pageable pageable) {
    return ResponseEntity.ok(
        new SuccessResponseBody<>(
            shippingManagerService.getShippingManagerInfos(
                jwtClaim.getUserId(), jwtClaim.getRole(), keyword, pageable)));
  }

  @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_MASTER','ROLE_HUB_MANAGER')")
  @DeleteMapping("/{shippingManagerId}")
  public ResponseEntity<ResponseBody<Void>> deleteShippingManager(
      @PathVariable UUID shippingManagerId, @AuthenticationPrincipal JwtClaim jwtClaim) {
    shippingManagerService.deleteShippingManager(shippingManagerId, jwtClaim.getUsername());
    return ResponseEntity.ok(new SuccessResponseBody<>());
  }
}
