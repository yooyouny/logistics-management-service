package com.sparta.user.presentation.controller.user;

import com.sparta.commons.domain.jwt.JwtClaim;
import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.user.application.dto.UserInfo;
import com.sparta.user.application.service.user.UserService;
import com.sparta.user.presentation.request.UpdateAuthorityRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/me")
  public ResponseEntity<ResponseBody<UserInfo>> getUserInfo(
      @AuthenticationPrincipal JwtClaim claim) {
    return ResponseEntity.ok(new SuccessResponseBody<>(userService.getUserInfo(claim.getUserId())));
  }

  @PreAuthorize("isAuthenticated() and hasRole('ROLE_MASTER')")
  @PatchMapping("/{userId}/authority")
  public ResponseEntity<ResponseBody<UserInfo>> updateUserAuthority(
      @PathVariable Long userId, @RequestBody @Valid UpdateAuthorityRequest request) {
    userService.updateUserAuthority(userId, request.role());
    return ResponseEntity.ok(new SuccessResponseBody<>());
  }

  @PreAuthorize("isAuthenticated() and hasRole('ROLE_MASTER')")
  @DeleteMapping("/{userId}")
  public ResponseEntity<ResponseBody<UserInfo>> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.ok(new SuccessResponseBody<>());
  }

  @PreAuthorize("isAuthenticated() and hasRole('ROLE_MASTER')")
  @GetMapping
  public ResponseEntity<ResponseBody<Page<UserInfo>>> getUserInfos(
      @AuthenticationPrincipal JwtClaim claim,
      Pageable pageable,
      @RequestParam(required = false) String keyword) {
    return ResponseEntity.ok(
        new SuccessResponseBody<>(userService.getUserInfos(claim.getUserId(), keyword, pageable)));
  }
}
