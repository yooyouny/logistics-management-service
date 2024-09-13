package com.sparta.hub.application.service;

import com.sparta.hub.infrastructure.repository.hub.HubRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubInternalService {

  private final HubRepository hubRepository;

  public boolean checkHubExists(UUID hubId) {
    return hubRepository.findById(hubId).isPresent();
  }
}
