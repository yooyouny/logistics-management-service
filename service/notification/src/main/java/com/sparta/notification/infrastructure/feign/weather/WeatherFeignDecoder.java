package com.sparta.notification.infrastructure.feign.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WeatherFeignDecoder implements Decoder {

  private final ObjectMapper objectMapper;

  @Override
  public Object decode(Response response, Type type)
      throws IOException, DecodeException, FeignException {
    if (type == WeatherResponse.items.class) {
      JsonNode jsonNode = objectMapper.readTree(response.body().asInputStream());
      WeatherDeserializer deserializer = new WeatherDeserializer(objectMapper);
      return deserializer.deserialize(jsonNode);
    }
    return objectMapper.readValue(response.body().asInputStream(), objectMapper.constructType(type));
  }
}