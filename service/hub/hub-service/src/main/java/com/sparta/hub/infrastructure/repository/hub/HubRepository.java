package com.sparta.hub.infrastructure.repository.hub;

import com.sparta.hub.domain.Hub;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID>, HubRepositoryCustom {

  Optional<Hub> findByHubIdAndIsDeleteFalse(UUID id);
}
