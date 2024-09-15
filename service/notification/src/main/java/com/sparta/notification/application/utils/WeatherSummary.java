package com.sparta.notification.application.utils;

import lombok.Setter;

@Setter
public class WeatherSummary {
  private String temperature = "N/A";
  private String humidity = "N/A";
  private String precipitation = "N/A";
  private String windSpeed = "N/A";
  private String precipitationType = "없음";

  @Override
  public String toString() {
    return String.format("현재 기온: %s, 습도: %s, 1시간 강수량: %s, 풍속: %s, 강수형태: %s입니다.",
        temperature, humidity, precipitation, windSpeed, precipitationType);
  }
}