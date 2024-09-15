package com.sparta.notification.infrastructure.configuration;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.sparta.notification.infrastructure.configuration.properties.SlackProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@EnableConfigurationProperties(SlackProperties.class)
@Configuration
public class SlackConfig {

  private final SlackProperties slackProperties;

  @Bean
  public MethodsClient getClient() {
    Slack slackClient = Slack.getInstance();
    return slackClient.methods(slackProperties.getToken());
  }
}
