package com.sparta.notification.infrastructure.feign.ai;

import com.sparta.notification.infrastructure.feign.ai.GenerateContentRequest.Content.Part;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateContentRequest {
  private List<Content> contents;

  public static GenerateContentRequest createQuestion(String text) {
    Part part = new Part(text);
    Content content = new Content(Collections.singletonList(part));
    return new GenerateContentRequest(Collections.singletonList(content));
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Content {
    private List<Part> parts;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Part {
      private String text;
    }
  }
}
