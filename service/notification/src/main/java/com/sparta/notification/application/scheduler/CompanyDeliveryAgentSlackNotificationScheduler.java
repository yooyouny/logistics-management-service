package com.sparta.notification.application.scheduler;

import com.sparta.notification.application.utils.SlackNotificationSender;
import com.sparta.notification.application.utils.WeatherSummary;
import com.sparta.notification.infrastructure.configuration.properties.WeatherProperties;
import com.sparta.notification.infrastructure.feign.ai.GeminiFeignClient;
import com.sparta.notification.infrastructure.feign.ai.GenerateContentRequest;
import com.sparta.notification.infrastructure.feign.ai.GenerateContentResponse;
import com.sparta.notification.infrastructure.feign.delivery.DeliveryFeignClient;
import com.sparta.notification.infrastructure.feign.user.UserFeignClient;
import com.sparta.notification.infrastructure.feign.weather.WeatherFeignClient;
import com.sparta.notification.infrastructure.feign.weather.WeatherResponse;
import com.sparta.notification.infrastructure.feign.weather.WeatherResponse.Item;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties(WeatherProperties.class)
@Slf4j
@RequiredArgsConstructor
@Component
public class CompanyDeliveryAgentSlackNotificationScheduler {

  public static final String SUMMARIZE_QUESTION_FROM = "현재 날씨는 %s이고, 배송 목록은 다음과 같습니다: %s. 이 두 가지를 종합하여 요약해 주세요.";

  private final WeatherFeignClient weatherFeignClient;
  private final WeatherProperties weatherProperties;
  private final UserFeignClient userFeignClient;
  private final DeliveryFeignClient deliveryFeignClient;
  private final GeminiFeignClient geminiFeignClient;
  private final SlackNotificationSender slackNotificationSender;


  //  @Scheduled(cron = "*/10 * * * * ?") // test
  @Scheduled(cron = "0 0 6 * * ?")
  public void run() throws IOException {
    // 1. 공공 데이터 포털의 날씨 API를 사용하여 해당일의 날씨 정보를 가져오기
    List<Item> items = fetchWeatherData();

    // 2. 유저서버로 부터 허브 배송 담당자 id 목록 받아오기
    List<UUID> hubDeliveryAgentIdList = userFeignClient.getHubDeliveryAgentIdList();

    for (UUID hubDeliveryAgentId : hubDeliveryAgentIdList) {
      // 3. 배송 서버로부터 해당 배송 담당자 id 기반 배송 목록 받아오기
      List<String> deliveryList = fetchDeliveryList(hubDeliveryAgentId);

      // 4. 질문 작성해서 gemini api 로 요약 요청 보내기
      String question = createSummaryQuestion(items, deliveryList);
      String responseText = requestSummaryFromGemini(question);

      // 5. 슬랙 알림 보내기
      notifySlack(responseText);
    }
  }

  private List<Item> fetchWeatherData() {
    String serviceKey = weatherProperties.getServiceKey();
    String baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String baseTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH00"));
    int nx = 55; // TODO: 위치 정보 동적 할당 필요??
    int ny = 127;

    List<Item> items = getWeatherData(serviceKey, baseDate, baseTime, nx, ny);
    log.info("weatherData : {}", items);
    return items;
  }

  private List<String> fetchDeliveryList(UUID hubDeliveryAgentId) {
    return deliveryFeignClient.getDeliveryListByShippingManagerId(hubDeliveryAgentId, LocalDateTime.now());
  }

  private String createSummaryQuestion(List<Item> items, List<String> deliveryList) {
    String weatherSummary = summarizeWeatherData(items);
    String deliverySummary = ""; // TODO. deliveryList 로 부터 요약 정보 String 반환
    return String.format(SUMMARIZE_QUESTION_FROM, weatherSummary, deliverySummary);
  }

  private String requestSummaryFromGemini(String question) {
    GenerateContentResponse response = geminiFeignClient.generateContent(
        GenerateContentRequest.createQuestion(question)
    );
    String responseText = response.getCandidates().get(0).getContent().getParts().get(0).getText();
    log.info("text : {}", responseText);
    return responseText;
  }

  private void notifySlack(String message) throws IOException {
    slackNotificationSender.execute(message);
  }

  private List<Item> getWeatherData(String serviceKey, String baseDate, String baseTime, int nx,
      int ny) {
    WeatherResponse.items weatherData = weatherFeignClient.getWeatherData(
        serviceKey,
        1,
        1000,
        "JSON",
        baseDate,
        baseTime,
        nx,
        ny);

    return weatherData.getItem();
  }

  private String summarizeWeatherData(List<Item> items) {
    StringBuilder summary = new StringBuilder();
    WeatherSummary weatherSummary = new WeatherSummary();

    for (Item item : items) {
      switch (item.getCategory()) {
        case "T1H":
          weatherSummary.setTemperature(item.getObsrValue() + "℃");
          break;
        case "REH":
          weatherSummary.setHumidity(item.getObsrValue() + "%");
          break;
        case "RN1":
          weatherSummary.setPrecipitation(item.getObsrValue() + "mm");
          break;
        case "WSD":
          weatherSummary.setWindSpeed(item.getObsrValue() + "m/s");
          break;
        case "PTY":
          weatherSummary.setPrecipitationType(getPrecipitationType(item.getObsrValue()));
          break;
        default:
          break;
      }
    }

    summary.append(weatherSummary);
    return summary.toString();
  }

  private String getPrecipitationType(String obsrValue) {
    int ptyValue = Integer.parseInt(obsrValue);
    return switch (ptyValue) {
      case 0 -> "없음";
      case 1 -> "비";
      case 2 -> "비/눈";
      case 3 -> "눈";
      case 5 -> "빗방울";
      case 6 -> "빗방울눈날림";
      case 7 -> "눈날림";
      case 4 -> "소나기";
      default -> "알 수 없음";
    };
  }
}
