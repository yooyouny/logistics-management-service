package com.sparta.hub.application.dto.interhub;

import java.util.UUID;
import lombok.Data;

@Data
public class InterHubSearchCond {

  private UUID departureHubId;
  private UUID arrivalHubId;
  private String departureHubName;
  private String arrivalHubName;
}
