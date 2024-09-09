package com.sparta.hub.presentation;

import com.sparta.commons.domain.jpa.BaseEntity;
import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.hub.application.dto.interhub.InterHubCreateRequest;
import com.sparta.hub.application.dto.interhub.InterHubResponse;
import com.sparta.hub.application.dto.interhub.InterHubUpdateRequest;
import com.sparta.hub.application.service.InterHubService;
import com.sparta.hub.domain.InterHub;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inter-hubs")
@Slf4j
public class InterHubController extends BaseEntity {

    private final InterHubService interHubService;

    @PostMapping
    public ResponseBody<InterHubResponse> createInterHubRoute(@Valid @RequestBody InterHubCreateRequest requestDto) {
        InterHubResponse route = interHubService.createRoute(requestDto);
        return new SuccessResponseBody<>(route);
    }

    @PutMapping("/{interHubId}")
    public ResponseBody<InterHubResponse> updateInterHubRoute(@Valid @RequestBody InterHubUpdateRequest requestDto, @PathVariable UUID interHubId) {
        InterHubResponse interHubResponse = interHubService.updateRoute(requestDto, interHubId);
        return new SuccessResponseBody<>(interHubResponse);
    }

    @DeleteMapping("/{interHubId}")
    public ResponseBody<UUID> deleteInterHubRoute(@PathVariable UUID interHubId, @RequestHeader(value = "X_Email", required = false) String email) {
        interHubService.delete(interHubId, email);
        return new SuccessResponseBody<>(interHubId);
    }
}
