package com.sparta.notification.domain.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Document(collection = "slack_notification")
public class SlackNotification {

  @Id private String id;

  private String slackId;

  private String message;

  private LocalDateTime sentAt;

  @Builder(access = AccessLevel.PRIVATE)
  public SlackNotification(String slackId, LocalDateTime sentAt, String message) {
    this.slackId = slackId;
    this.sentAt = sentAt;
    this.message = message;
  }

  public static SlackNotification create(String slackId, String message, LocalDateTime sentAt) {
    return SlackNotification.builder().slackId(slackId).message(message).sentAt(sentAt).build();
  }
}
