package com.sparta.hub.application.dto.interhub;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
public class InterHubCreateRequest {

  @NotNull
  private UUID departureHubId;
  @NotNull
  private UUID arrivalHubId;
}
