package com.sparta.notification.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class TaskSchedulerConfig implements SchedulingConfigurer {

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();
    int n = Runtime.getRuntime().availableProcessors(); // core 갯수
    threadPool.setPoolSize(n * 2); // 쓰레드 풀 갯수 설정
    threadPool.initialize();
    taskRegistrar.setTaskScheduler(threadPool);
  }
}
