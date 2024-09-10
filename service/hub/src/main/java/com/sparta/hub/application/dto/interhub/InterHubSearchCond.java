package com.sparta.hub.application.dto.interhub;

import lombok.Data;

import java.util.UUID;

@Data
public class InterHubSearchCond {
    private UUID departureHubId;
    private UUID arrivalHubId;
    private String departureHubName;
    private String arrivalHubName;
}
