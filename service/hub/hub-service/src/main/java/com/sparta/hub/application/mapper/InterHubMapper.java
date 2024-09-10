package com.sparta.hub.application.mapper;

import com.sparta.hub.application.dto.interhub.InterHubResponse;
import com.sparta.hub.domain.Hub;
import com.sparta.hub.domain.InterHub;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Dto to Entity ,  Entity to Dto
@Component
public class InterHubMapper {

  public InterHubResponse toResponse(InterHub interHub) {
    InterHubResponse response = new InterHubResponse();
    response.setInterHubId(interHub.getInterHubId());
    response.setDepartureHubId(interHub.getDepartureHub().getHubId());
    response.setArrivalHubId(interHub.getArrivalHub().getHubId());
    response.setDistance(interHub.getDistance());
    response.setElapsedTime(interHub.getElapsedTime());
    return response;
  }

  public List<InterHubResponse> toResponse(InterHub interHub1, InterHub interHub2) {
    List<InterHubResponse> response = new ArrayList<>();
    InterHubResponse response1 = toResponse(interHub1);
    InterHubResponse response2 = toResponse(interHub2);
    response.add(response1);
    response.add(response2);
    return response;
  }


}
