package com.sparta.notification.infrastructure.feign.ai;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "geminiFeignClient",
    url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent",
    configuration = GeminiFeignConfig.class)
public interface GeminiFeignClient {

  @PostMapping
  GenerateContentResponse generateContent(@RequestBody GenerateContentRequest request);
}
