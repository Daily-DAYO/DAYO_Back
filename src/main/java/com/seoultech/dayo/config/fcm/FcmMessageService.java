package com.seoultech.dayo.config.fcm;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.seoultech.dayo.alarm.Topic;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FcmMessageService {

  private final FirebaseMessaging firebaseMessaging;

  public FcmMessageService(FirebaseMessaging firebaseMessaging) {
    this.firebaseMessaging = firebaseMessaging;
  }

  public void unsubscribeFromTopic() throws FirebaseMessagingException {
    List<String> deviceTokens = deviceTokens();
    firebaseMessaging.unsubscribeFromTopic(deviceTokens, Topic.NOTICE.toString());
  }

  public void subscribeToTopic() throws FirebaseMessagingException {
    List<String> deviceTokens = deviceTokens();
    firebaseMessaging.subscribeToTopic(deviceTokens, Topic.NOTICE.toString());
  }

  public void sendMessage(Note note, String topic) {

    Notification notification = Notification.builder()
        .setTitle(null)
        .setBody(note.getContent())
        .build();

    Message message = Message.builder()
        .setToken(note.getDeviceToken())
//        .setNotification(notification)
        .putAllData(note.getData())
//        .setTopic(topic)
        .build();

    try {
      firebaseMessaging.send(message);
    } catch (FirebaseMessagingException e) {
      e.printStackTrace();
    }
  }

  private List<String> deviceTokens() {
    return null;
  }
}
