package com.sparta.user.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.user.domain.model.User;
import java.time.LocalDateTime;

public record UserInfo(
    Long id,
    String username,
    String email,
    String role,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt) {
  public static UserInfo create(User user) {
    return new UserInfo(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getRole().name(),
        user.getCreatedAt(),
        user.getUpdatedAt());
  }
}
