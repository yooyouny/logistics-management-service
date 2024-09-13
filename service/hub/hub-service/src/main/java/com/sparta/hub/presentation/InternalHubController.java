package com.sparta.hub.presentation;

import com.sparta.hub.application.service.HubInternalService;
import com.sparta.hub.application.service.HubService;
import com.sparta.hub.domain.Hub;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/hubs")
public class InternalHubController {

  private final HubInternalService hubService;

  @GetMapping("/{hubId}")
  public boolean checkHubExists(@PathVariable UUID hubId) {
    return hubService.checkHubExists(hubId);
  }

}
