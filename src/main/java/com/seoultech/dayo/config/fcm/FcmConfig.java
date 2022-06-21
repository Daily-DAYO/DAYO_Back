package com.seoultech.dayo.config.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FcmConfig {

  @Bean
  public FirebaseMessaging firebaseMessaging() throws IOException {

    GoogleCredentials googleCredentials = GoogleCredentials
        .fromStream(new ClassPathResource("fcm/dayo-74150.json").getInputStream());
    FirebaseOptions firebaseOptions = FirebaseOptions
        .builder()
        .setCredentials(googleCredentials)
        .build();
    FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "dayo-74150");
    return FirebaseMessaging.getInstance(app);

  }


}
