package com.sparta.hub.infrastructure.repository.interhub;

import com.sparta.hub.domain.InterHub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InterHubRepository extends JpaRepository<InterHub, UUID>,
    InterHubRepositoryCustom {

}
