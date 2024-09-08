package com.sparta.hub.infrastructure.repository.hub;

import com.sparta.hub.application.dto.hub.HubResponse;
import com.sparta.hub.application.dto.hub.HubSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRepositoryCustom {

    Page<HubResponse> searchHub(Pageable pageable, HubSearchCond cond);
}
