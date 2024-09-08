package com.sparta.hub.application.dto.interhub;

import lombok.Data;

import java.util.UUID;

@Data
public class InterHubCreateRequest {

    private UUID departureHubId;
    private UUID arrivalHubId;
}
