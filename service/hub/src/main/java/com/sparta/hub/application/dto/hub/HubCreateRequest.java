package com.sparta.hub.application.dto.hub;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HubCreateRequest {

  @NotNull
  private Long userId;
  @NotNull
  private String hubName;
  @NotNull
  private String hubAddress;
  @NotNull
  @Digits(integer = 3, fraction = 4)  // 정수부 최대 3자리, 소수부 최대 4자리
  private BigDecimal hubLatitude;
  @NotNull
  @Digits(integer = 3, fraction = 4)  // 정수부 최대 3자리, 소수부 최대 4자리
  private BigDecimal hubLongitude;
}
