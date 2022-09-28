package com.seoultech.dayo.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

  private final MailService mailService;

  @GetMapping
  public void send(@RequestParam("email") String email) {
    mailService.sendAuthMail(email);

  }


}
