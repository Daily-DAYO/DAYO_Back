package com.seoultech.dayo.utils.notification;

import com.seoultech.dayo.alarm.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
final class KafkaProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(Topic topic, String message) {
    kafkaTemplate.send(topic.toString(), message);
  }

}
