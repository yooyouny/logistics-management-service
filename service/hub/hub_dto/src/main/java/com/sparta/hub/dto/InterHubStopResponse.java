package com.sparta.hub.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class InterHubStopResponse implements Serializable {

  private Long interHubStopId;
  private int sequence;
  private UUID arrivalHubId;
  private UUID departureHubId;
  private Long elapsedTime;  // 소요시간
  private double distance;

}
