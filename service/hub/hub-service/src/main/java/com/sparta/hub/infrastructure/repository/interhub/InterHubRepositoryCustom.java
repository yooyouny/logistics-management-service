package com.sparta.hub.infrastructure.repository.interhub;

import com.sparta.hub.application.dto.interhub.InterHubSearchCond;
import com.sparta.hub.dto.InterHubResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InterHubRepositoryCustom {

  Page<InterHubResponse> searchHub(Pageable pageable, InterHubSearchCond cond);
}
