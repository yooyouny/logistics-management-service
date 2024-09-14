package com.sparta.hub.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HubResponse implements Serializable {

  private UUID hubId;
  private Long userId;
  private String hubName;
  private String hubAddress;
  private BigDecimal hubLatitude;
  private BigDecimal hubLongitude;
}
