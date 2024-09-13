package com.sparta.user.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.user.domain.model.ShippingManager;
import com.sparta.user.domain.model.vo.ShippingManagerType;
import java.time.LocalDateTime;
import java.util.UUID;

public record SippingManagerInfo(
    UUID id,
    String slackId,
    ShippingManagerType type,
    Boolean isDelete,
    UUID hubId,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdAt,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime updatedAt
) {
  public static SippingManagerInfo create(ShippingManager shippingManager) {
    return new SippingManagerInfo(
        shippingManager.getId(),
        shippingManager.getSlackId(),
        shippingManager.getType(),
        shippingManager.getIsDelete(),
        shippingManager.getHubId(),
        shippingManager.getCreatedAt(),
        shippingManager.getUpdatedAt()
    );
  }
}
