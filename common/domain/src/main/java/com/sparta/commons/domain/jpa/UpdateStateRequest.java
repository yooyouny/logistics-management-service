package com.sparta.commons.domain.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateStateRequest {
  private String requestedId;
  private String targetId;
  private String stateName;

  @Builder
  public UpdateStateRequest(String requestedId, String targetId, String stateName) {
    this.requestedId = requestedId;
    this.targetId = targetId;
    this.stateName = stateName;
  }
}
