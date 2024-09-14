package com.sparta.notification.application.service;

import com.slack.api.model.block.LayoutBlock;
import com.sparta.notification.infrastructure.configuration.properties.SlackProperties;
import com.sparta.notification.infrastructure.slack.SlackHelper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackNotificationProvider {

  private final SlackHelper slackHelper;
  private final SlackProperties slackProperties;

  @Async
  public void sendNotification(List<LayoutBlock> layoutBlocks) {
    slackHelper.sendNotification(slackProperties.getChannel(), layoutBlocks);
  }

}
