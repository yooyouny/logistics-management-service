package com.sparta.hub.application.dto.interhub;

import java.io.Serializable;
import lombok.Data;

import java.util.UUID;

@Data
public class InterHubResponse implements Serializable {

    private UUID interHubId;
    private UUID departureHubId;
    private UUID arrivalHubId;
    private Long elapsedTime;
}
