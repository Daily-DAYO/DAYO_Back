package com.seoultech.dayo.config.fcm;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;
import com.seoultech.dayo.alarm.Topic;
import org.springframework.stereotype.Service;

@Service
public class FcmMessageService {

  private final FirebaseMessaging firebaseMessaging;

  public FcmMessageService(FirebaseMessaging firebaseMessaging) {
    this.firebaseMessaging = firebaseMessaging;
  }

  public String sendMessage(Note note, String token, Topic topic)
      throws FirebaseMessagingException {

    Notification notification = Notification.builder()
        .setTitle(note.getSubject())
        .setBody(note.getContent())
        .build();

    Message message = Message.builder()
        .setToken(token)
        .setNotification(notification)
        .putAllData(note.getData())
        .setTopic(topic.toString())
        .build();

    return firebaseMessaging.send(message);
  }
}
