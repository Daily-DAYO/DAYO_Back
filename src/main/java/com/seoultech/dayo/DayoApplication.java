package com.seoultech.dayo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DayoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DayoApplication.class, args);
  }

}
