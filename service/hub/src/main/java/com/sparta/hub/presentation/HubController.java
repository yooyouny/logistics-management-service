package com.sparta.hub.presentation;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.hub.application.dto.hub.HubCreateRequest;
import com.sparta.hub.application.dto.hub.HubResponse;
import com.sparta.hub.application.dto.hub.HubSearchCond;
import com.sparta.hub.application.dto.hub.HubUpdateRequest;
import com.sparta.hub.application.service.HubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hubs")
public class HubController {

    private final HubService hubService;

    //TODO 접근 권한 제어, User
    @PostMapping
    public ResponseBody<HubResponse> createHub(@RequestBody HubCreateRequest hubCreateRequest) {
        HubResponse response = hubService.createHub(hubCreateRequest);
        return new SuccessResponseBody<>(response);
    }

    //TODO 접근 권한 제어
    @PutMapping("/{hubId}")
    public ResponseBody<HubResponse> updateHub(@Valid @RequestBody HubUpdateRequest requestDto, @PathVariable UUID hubId) {
        HubResponse hubResponse = hubService.updateHub(requestDto, hubId);
        return new SuccessResponseBody<>(hubResponse);
    }

    //TODO 접근 권한 제어
    @DeleteMapping("/{hubId}")
    public ResponseBody<UUID> deleteHub(@RequestHeader(value = "X_Email", required = false) String email,@PathVariable UUID hubId) {
        hubService.deleteHub(hubId,email);
        return new SuccessResponseBody<>(hubId);
    }

    @GetMapping("/{hubId}")
    public ResponseBody<HubResponse> getSingleHub(@PathVariable UUID hubId) {
        HubResponse responseHub = hubService.getSingleHub(hubId);
        return new SuccessResponseBody<>(responseHub);
    }

    @GetMapping
    public ResponseBody<Page<HubResponse>> getAllHubs(Pageable pageable, HubSearchCond cond) {
        Page<HubResponse> allHub = hubService.getAllHub(pageable, cond);
        return new SuccessResponseBody<>(allHub);
    }



}
