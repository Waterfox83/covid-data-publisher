package com.jda.covidpublisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//CSOFF: HideUtilityClassConstructor
@SpringBootApplication
@EnableAsync
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  public static final class Profiles {
    public static final String TEST = "test";
  }

  @Bean("threadPoolTaskExecutor")
  public TaskExecutor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(20);
    executor.setMaxPoolSize(1000);
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setThreadNamePrefix("Async-");
    return executor;
  }

}
//CSON: HideUtilityClassConstructor
