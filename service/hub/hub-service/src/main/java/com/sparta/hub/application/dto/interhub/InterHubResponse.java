package com.sparta.hub.application.dto.interhub;

import com.sparta.hub.dto.InterHubStopResponse;
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
