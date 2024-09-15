package com.sparta.notification.domain.repository;

import com.sparta.notification.domain.model.SlackNotification;
import org.springframework.stereotype.Repository;

@Repository
public interface SlackNotificationRepository {

  void save(SlackNotification slackNotification);
}
