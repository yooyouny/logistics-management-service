package com.sparta.hub.application.mapper;

import com.sparta.hub.domain.InterHub;
import com.sparta.hub.domain.InterHubStop;
import com.sparta.hub.dto.InterHubResponse;
import com.sparta.hub.dto.InterHubStopResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

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
    response.setStops(toResponse(interHub.getInterHubStops()));
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

  public InterHubStopResponse toInterHubStopResponse(InterHubStop interHubStop) {
    InterHubStopResponse response = new InterHubStopResponse();
    response.setDepartureHubId(interHubStop.getDepartureHubId());
    response.setArrivalHubId(interHubStop.getArrivalHubId());
    response.setDistance(interHubStop.getDistance());
    response.setElapsedTime(interHubStop.getElapsedTime());
    response.setInterHubStopId(interHubStop.getInterHubStopId());
    response.setSequence(interHubStop.getSequence());
    return response;
  }

  public List<InterHubStopResponse> toResponse(List<InterHubStop> interHubStops) {
    List<InterHubStopResponse> response = new ArrayList<>();
    for (InterHubStop interHubStop : interHubStops) {
      InterHubStopResponse interHubStopResponse = toInterHubStopResponse(interHubStop);
      response.add(interHubStopResponse);
    }
    return response;
  }


}
