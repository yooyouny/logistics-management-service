package com.sparta.hub.application.dto.interhub;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class InterHubUpdateRequest {

  @NotNull
  private UUID departureHubId;
  @NotNull
  private UUID arrivalHubId;
  @NotNull
  private Long elapsedTime;

}
