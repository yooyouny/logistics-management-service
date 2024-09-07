package com.sparta.hub.infrastructure.repository;

import com.sparta.hub.application.dto.HubResponse;
import com.sparta.hub.application.dto.HubSearchCond;
import com.sparta.hub.domain.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HubRepositoryCustom {

    Page<HubResponse> searchHub(Pageable pageable, HubSearchCond cond);
}
