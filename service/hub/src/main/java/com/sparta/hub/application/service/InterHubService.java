package com.sparta.hub.application.service;

import com.sparta.hub.infrastructure.repository.interhub.InterHubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InterHubService {

    private final InterHubRepository interHubRepository;
}
