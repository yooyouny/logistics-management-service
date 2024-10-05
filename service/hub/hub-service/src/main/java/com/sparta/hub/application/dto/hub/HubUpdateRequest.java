package com.sparta.hub.application.dto.hub;

import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class HubUpdateRequest {

  private String hubName;
  private String hubAddress;

  @Digits(integer = 3, fraction = 4) // 정수부 최대 3자리, 소수부 최대 4자리
  private BigDecimal hubLatitude;

  @Digits(integer = 3, fraction = 4) // 정수부 최대 3자리, 소수부 최대 4자리
  private BigDecimal hubLongitude;
}
