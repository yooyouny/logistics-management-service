package com.sparta.notification.infrastructure.slack;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.LayoutBlock;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SlackHelper {

  private final MethodsClient methodsClient;

  public void sendNotification(String CHANNEL_ID, List<LayoutBlock> layoutBlocks) {
    ChatPostMessageRequest chatPostMessageRequest =
        ChatPostMessageRequest.builder()
            .channel(CHANNEL_ID)
            .text("test")
            .blocks(layoutBlocks)
            .build();
    try {
      methodsClient.chatPostMessage(chatPostMessageRequest);
    } catch (SlackApiException | IOException slackApiException) {
      log.error(slackApiException.toString());
    }
  }
}
