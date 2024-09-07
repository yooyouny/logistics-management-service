package com.sparta.hub.application.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HubUpdateRequest {

    private String hubName;
    private String hubAddress;
    @Digits(integer = 3, fraction = 4)  // 정수부 최대 3자리, 소수부 최대 4자리
    private BigDecimal hubLatitude;
    @Digits(integer = 3, fraction = 4)  // 정수부 최대 3자리, 소수부 최대 4자리
    private BigDecimal hubLongitude;
}
