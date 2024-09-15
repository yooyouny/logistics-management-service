package com.sparta.company.presentation.controller.ai;

import com.sparta.company.infrastructure.ai.GoogleAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/ai")
@RequiredArgsConstructor
public class AiController {

  private final GoogleAiService aiService;

  @PostMapping
  public String generateContent(@RequestParam String inputText) {
    return aiService.generateContent(inputText);
  }
}
