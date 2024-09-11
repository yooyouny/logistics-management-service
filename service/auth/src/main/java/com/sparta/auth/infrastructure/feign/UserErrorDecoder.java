package com.sparta.auth.infrastructure.feign;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.auth.exception.BusinessException;
import com.sparta.auth.exception.ErrorCode;
import com.sparta.auth.exception.FeignClientException;
import com.sparta.commons.domain.response.FailedResponseBody;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    Optional<FailedResponseBody> errorData = this.parse(response);
    if (errorData.isPresent()) {
      switch (response.status()) {
        case 404, 409:
          return new FeignClientException(response.status(), errorData.get().getCode(),
              errorData.get().getMsg());
      }
    }
    return new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
  }

  private Optional<FailedResponseBody> parse(Response response) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      String body = new String(response.body().asInputStream().readAllBytes());
      FailedResponseBody responseBody = objectMapper.readValue(body, FailedResponseBody.class);
      return Optional.of(responseBody);
    } catch (IOException e) {
      log.error("Error parsing response body", e);
      return Optional.empty();
    }
  }
}
