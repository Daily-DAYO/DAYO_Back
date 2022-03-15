package com.seoultech.dayo.fcm;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class FcmMessageService {

  private final FirebaseMessaging firebaseMessaging;

  public FcmMessageService(FirebaseMessaging firebaseMessaging) {
    this.firebaseMessaging = firebaseMessaging;
  }

  public String sendMessage(Note note, String token) throws FirebaseMessagingException {

    Notification notification = Notification.builder()
        .setTitle(note.getSubject())
        .setBody(note.getContent())
        .build();

    Message message = Message.builder()
        .setToken(token)
        .setNotification(notification)
        .putAllData(note.getData())
        .build();

    return firebaseMessaging.send(message);
  }
}
