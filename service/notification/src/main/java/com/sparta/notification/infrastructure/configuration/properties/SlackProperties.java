package com.sparta.notification.infrastructure.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "slack")
public class SlackProperties {
  private String token;
  private String channel;
}
