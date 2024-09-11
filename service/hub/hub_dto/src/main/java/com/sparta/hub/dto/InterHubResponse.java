package com.sparta.hub.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class InterHubResponse implements Serializable {

  private UUID interHubId;
  private UUID departureHubId;
  private UUID arrivalHubId;
  private Double distance;
  private Long elapsedTime;
  private List<InterHubStopResponse> stops;
}
