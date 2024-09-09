package com.sparta.hub.presentation;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.hub.application.dto.interhub.InterHubCreateRequest;
import com.sparta.hub.application.dto.interhub.InterHubResponse;
import com.sparta.hub.application.service.InterHubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inter-hubs")
@Slf4j
public class InterHubController {

    private final InterHubService interHubService;

    @PostMapping
    public ResponseBody<InterHubResponse> createInterHubRoute(@Valid @RequestBody InterHubCreateRequest requestDto) {
        InterHubResponse route = interHubService.createRoute(requestDto);
        return new SuccessResponseBody<>(route);
    }
}
