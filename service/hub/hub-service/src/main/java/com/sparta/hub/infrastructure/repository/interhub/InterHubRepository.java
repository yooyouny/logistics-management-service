package com.sparta.hub.infrastructure.repository.interhub;

import com.sparta.hub.domain.InterHub;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface InterHubRepository
    extends JpaRepository<InterHub, UUID>, InterHubRepositoryCustom {

  @Query(
      "select ih from InterHub ih where ih.departureHub.hubId = :departureHubId and ih.arrivalHub.hubId = :arrivalHubId")
  Optional<InterHub> findByDpHubAndAvHub(UUID departureHubId, UUID arrivalHubId);
}
