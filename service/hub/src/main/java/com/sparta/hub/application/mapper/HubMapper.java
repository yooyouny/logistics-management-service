package com.sparta.hub.application.mapper;

import com.sparta.hub.application.dto.hub.HubCreateRequest;
import com.sparta.hub.application.dto.hub.HubResponse;
import com.sparta.hub.domain.Hub;
import org.springframework.stereotype.Component;

@Component
public class HubMapper {

    public Hub createRequestToEntity(HubCreateRequest hubCreateRequest) {
        return Hub.builder()
                .userId(hubCreateRequest.getUserId())
                .hubName(hubCreateRequest.getHubName())
                .hubAddress(hubCreateRequest.getHubAddress())
                .hubLatitude(hubCreateRequest.getHubLatitude())
                .hubLongitude(hubCreateRequest.getHubLongitude())
                .isDelete(false)
                .build();
    }

    public HubResponse toResponse(Hub hub) {
        HubResponse response = new HubResponse();
        response.setUserId(hub.getUserId());
        response.setHubId(hub.getId());
        response.setHubName(hub.getHubName());
        response.setHubAddress(hub.getHubAddress());
        response.setHubLatitude(hub.getHubLatitude());
        response.setHubLongitude(hub.getHubLongitude());
        return response;
    }
}
