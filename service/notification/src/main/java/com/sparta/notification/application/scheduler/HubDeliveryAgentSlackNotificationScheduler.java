package com.sparta.notification.application.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HubDeliveryAgentSlackNotificationScheduler {

  @Scheduled(cron = "0 0 8 * * ?")
  public void run() {
    // 허브별 주문을 받아오기
    // 1. 허브 서버로 부터 모든 허브의 아이디를 받아오기
    // 2. 주문 서버로 부터 허브아이디를 통해 허브별 주문을 받아오기
    // 3. Gemini API 를 통해 요약하기
    // 4. 슬랙 알림 전송
  }
}
