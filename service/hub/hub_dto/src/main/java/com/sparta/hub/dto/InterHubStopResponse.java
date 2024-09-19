package com.sparta.hub.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class InterHubStopResponse {

  private Long interHubStopId;
  private int sequence;
  private UUID arrivalHubId;
  private UUID departureHubId;
  private Long elapsedTime; // 소요시간
  private double distance;
}
