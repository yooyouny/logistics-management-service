package com.sparta.hub.application.dto.interhub;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InterHubCreateRequest {

  @NotNull
  private UUID departureHubId;
  @NotNull
  private UUID arrivalHubId;
}
