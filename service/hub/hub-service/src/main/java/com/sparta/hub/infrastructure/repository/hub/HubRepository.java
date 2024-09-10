package com.sparta.hub.infrastructure.repository.hub;

import com.sparta.hub.domain.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HubRepository extends JpaRepository<Hub, UUID>, HubRepositoryCustom {

  Optional<Hub> findByHubIdAndIsDeleteFalse(UUID id);

}
