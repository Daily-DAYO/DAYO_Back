package com.seoultech.dayo.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmMessageController {

  private final FcmMessageService fcmMessageService;

  @PostMapping("/alarm")
  public String test(@RequestBody Note note, @RequestParam String token)
      throws FirebaseMessagingException {
    return fcmMessageService.sendMessage(note, token);
  }

}
