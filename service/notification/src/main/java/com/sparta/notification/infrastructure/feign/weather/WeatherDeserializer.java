package com.sparta.notification.infrastructure.feign.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WeatherDeserializer {

  private final ObjectMapper objectMapper;

  public WeatherResponse.items deserialize(JsonNode node) throws IOException {
    JsonNode responseNode = node.findValue("response");

    JsonNode itemNode = responseNode.get("body").get("items").get("item");
    WeatherResponse.Item[] items = objectMapper.treeToValue(itemNode, WeatherResponse.Item[].class);

    // 필요한 카테고리만 필터링
    List<WeatherResponse.Item> filteredItems = new ArrayList<>();
    for (WeatherResponse.Item item : items) {
      String category = item.getCategory();
      if (category.equals("T1H") || category.equals("RN1") ||
          category.equals("REH") || category.equals("PTY") ||
          category.equals("WSD")) {
        filteredItems.add(item);
      }
    }

    return new WeatherResponse.items(filteredItems);
  }
}