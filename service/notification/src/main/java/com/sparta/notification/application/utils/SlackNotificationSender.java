package com.sparta.notification.application.utils;

import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.sparta.notification.application.service.SlackNotificationProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackNotificationSender {

  private final SlackNotificationProvider slackProvider;

  public void execute(String message) throws IOException {
    List<LayoutBlock> layoutBlocks = new ArrayList<>();
    layoutBlocks.add(
        Blocks.header(
            headerBlockBuilder ->
                headerBlockBuilder.text(plainText("🚚 오늘의 배송정보 요약입니다."))));
    layoutBlocks.add(divider());

    MarkdownTextObject errorUserIdMarkdown =
        MarkdownTextObject.builder().text("* message :*\n" + message).build();
    layoutBlocks.add(
        section(
            section ->
                section.fields(List.of(errorUserIdMarkdown))));

    slackProvider.sendNotification(layoutBlocks);
  }
}
