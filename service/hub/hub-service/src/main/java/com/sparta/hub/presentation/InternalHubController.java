package com.sparta.hub.presentation;

import com.sparta.hub.application.service.HubService;
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

  private final HubService hubService;

  @GetMapping("/{hubId}")
  public ResponseEntity<Void> checkHubExists(@PathVariable UUID hubId) {
    boolean exists = hubService.checkHubExists(hubId);
    return exists ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }

}
