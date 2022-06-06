package com.seoultech.dayo.utils;

import com.seoultech.dayo.alarm.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaTest {

  private final KafkaProducer kafkaProducer;

  @GetMapping("/kafka/{message}")
  public void test(@PathVariable String message) {
    kafkaProducer.sendMessage(Topic.HEART, message);
  }


}
