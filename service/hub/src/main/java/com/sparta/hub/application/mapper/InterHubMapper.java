package com.sparta.hub.application.mapper;

import com.sparta.hub.application.dto.interhub.InterHubResponse;
import com.sparta.hub.domain.InterHub;
import org.springframework.stereotype.Component;

// Dto to Entity ,  Entity to Dto
@Component
public class InterHubMapper {

    public InterHubResponse toResponse(InterHub interHub) {
        InterHubResponse response = new InterHubResponse();
        response.setInterHubId(interHub.getId());
        response.setDepartureHubId(interHub.getDepartureHub().getId());
        response.setArrivalHubId(interHub.getArrivalHub().getId());
        response.setElapsedTime(interHub.getElapsedTime());
        return response;
    }
}
