package com.sparta.hub.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class HubResponse implements Serializable {

    private UUID hubId;
    private Long userId;
    private String hubName;
    private String hubAddress;
    private BigDecimal hubLatitude;
    private BigDecimal hubLongitude;
}
