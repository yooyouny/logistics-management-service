package com.sparta.notification.domain.repository;

import com.sparta.notification.domain.model.SlackNotification;
import com.sparta.notification.infrastructure.repository.SlackNotificationMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SlackNotificationImpl implements SlackNotificationRepository {

  private final SlackNotificationMongoRepository slackNotificationMongoRepository;

  @Override
  public void save(SlackNotification slackNotification) {
    slackNotificationMongoRepository.save(slackNotification);
  }
}
