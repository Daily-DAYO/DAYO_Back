package com.seoultech.dayo.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailService {

  private final JavaMailSender mailSender;
  private final SpringTemplateEngine springTemplateEngine;

  //인증메일 보내기
  public String sendAuthMail(String email) {
    //6자리 난수 인증번호 생성
    String authCode = getAuthCode();

    //인증메일 보내기
    try {
      MailUtils sendMail = new MailUtils(mailSender);
      Context context = new Context();
      context.setVariable("code", authCode);
      sendMail.setSubject("[DAYO] 회원가입 이메일 인증");
      sendMail.setText(springTemplateEngine.process("mail", context));
      sendMail.addInline("dayoLogo", "static/dayo.png");
      sendMail.setFrom("jdyj@naver.com", "관리자");
      sendMail.setTo(email);
      sendMail.send();
    } catch (MessagingException | IOException e) {
      e.printStackTrace();
    }

    return authCode;
  }

  //인증코드 난수 발생
  private String getAuthCode() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    int num = 0;

    while (sb.length() < 6) {
      num = random.nextInt(10);
      sb.append(num);
    }

    return sb.toString();
  }

}
