package com.sparta.notification.infrastructure.repository;

import com.sparta.notification.domain.model.SlackNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlackNotificationMongoRepository
    extends MongoRepository<SlackNotification, String> {}
