package com.sparta.notification.infrastructure.feign.weather;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class WeatherResponse {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class items {
    private List<Item> item;
  }

  @ToString
  @Getter
  @NoArgsConstructor
  public static class Item {
    private String baseDate;
    private String baseTime;
    private String category;
    private Long nx;
    private Long ny;
    private String obsrValue;
  }
}
