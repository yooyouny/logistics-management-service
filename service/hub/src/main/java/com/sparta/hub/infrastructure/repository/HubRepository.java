package com.sparta.hub.infrastructure.repository;

import com.sparta.hub.application.dto.HubSearchCond;
import com.sparta.hub.domain.Hub;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository extends JpaRepository<Hub, UUID>, HubRepositoryCustom {

    Optional<Hub> findByIdAndIsDeleteFalse(UUID id);

}
