package com.seoultech.dayo.mail;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

  private final JavaMailSender mailSender;

  //인증메일 보내기
  public String sendAuthMail(String email) {
    //6자리 난수 인증번호 생성
    String authKey = getAuthCode();

    //인증메일 보내기
    try {
      MailUtils sendMail = new MailUtils(mailSender);
      sendMail.setSubject("회원가입 이메일 인증");
      sendMail.setText("<h1>[이메일 인증]</h1>"
          + "<p>6자리 여기요~</p>"
          + authKey);
      sendMail.setFrom("jdyj@naver.com", "관리자");
      sendMail.setTo(email);
      sendMail.send();
    } catch (MessagingException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return authKey;
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
