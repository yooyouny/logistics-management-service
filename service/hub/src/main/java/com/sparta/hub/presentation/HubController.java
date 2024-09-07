package com.sparta.hub.presentation;

import com.sparta.hub.application.dto.HubCreateRequest;
import com.sparta.hub.application.dto.HubResponse;
import com.sparta.hub.application.dto.HubSearchCond;
import com.sparta.hub.application.dto.HubUpdateRequest;
import com.sparta.hub.application.service.HubService;
import com.sparta.hub.presentation.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto<HubResponse>> createHub(@RequestBody HubCreateRequest hubCreateRequest) {
        HubResponse response = hubService.createHub(hubCreateRequest);
        return ResponseEntity.ok(new ResponseDto<>("200", "허브가 생성되었습니다", response));
    }

    //TODO 접근 권한 제어
    @PutMapping("/{hubId}")
    public ResponseEntity<ResponseDto<HubResponse>> updateHub(@Valid @RequestBody HubUpdateRequest requestDto, @PathVariable UUID hubId) {
        HubResponse hubResponse = hubService.updateHub(requestDto, hubId);
        return ResponseEntity.ok(new ResponseDto<>("200", "허브가 수정되었습니다", hubResponse));
    }

    //TODO 접근 권한 제어
    @DeleteMapping("/{hubId}")
    public ResponseEntity<ResponseDto<UUID>> deleteHub(@PathVariable UUID hubId) {
        hubService.deleteHub(hubId);
        return ResponseEntity.ok(new ResponseDto<>("200", "허브가 삭제되었습니다.", hubId));
    }

    @GetMapping("/{hubId}")
    public ResponseEntity<ResponseDto<HubResponse>> getSingleHub(@PathVariable UUID hubId) {
        HubResponse responseHub = hubService.getSingleHub(hubId);
        return ResponseEntity.ok(new ResponseDto<>("200", "허브 단일 조회", responseHub));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<Page<HubResponse>>> getAllHubs(Pageable pageable, HubSearchCond cond) {
        Page<HubResponse> allHub = hubService.getAllHub(pageable, cond);
        return ResponseEntity.ok(new ResponseDto<>("200", "허브 전체 조회", allHub));
    }



}
