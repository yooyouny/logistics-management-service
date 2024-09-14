package com.sparta.hub.application.service;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.hub.application.dto.hub.HubResponse;
import com.sparta.hub.application.mapper.HubMapper;
import com.sparta.hub.domain.Hub;
import com.sparta.hub.exception.HubErrorCode;
import com.sparta.hub.infrastructure.repository.hub.HubRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubInternalService {

  private final HubRepository hubRepository;
  private final HubMapper hubMapper;

  public boolean checkHubExists(UUID hubId) {
    return hubRepository.findById(hubId).isPresent();
  }

  public Optional<HubResponse> getHubByCompany(UUID hubId) {
    System.out.println("hubId = " + hubId);
    Hub hub = hubRepository.findById(hubId)
        .orElseThrow(() -> new BusinessException(HubErrorCode.NOT_FOUND));
    return Optional.of(hubMapper.toResponse(hub));
  }
}
