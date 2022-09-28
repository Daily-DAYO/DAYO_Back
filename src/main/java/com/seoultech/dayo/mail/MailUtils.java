package com.seoultech.dayo.mail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtils {

  private final JavaMailSender mailSender;
  private final MimeMessage message;
  private final MimeMessageHelper messageHelper;

  public MailUtils(JavaMailSender mailSender) throws MessagingException {
    this.mailSender = mailSender;
    message = this.mailSender.createMimeMessage();
    messageHelper = new MimeMessageHelper(message, true, "UTF-8");
  }

  public void setSubject(String subject) throws MessagingException {
    messageHelper.setSubject(subject);
  }

  public void setText(String htmlContent) throws MessagingException {
    messageHelper.setText(htmlContent, true);
  }

  public void setFrom(String email, String name)
      throws UnsupportedEncodingException, MessagingException {
    messageHelper.setFrom(email, name);
  }

  public void setTo(String email) throws MessagingException {
    messageHelper.setTo(email);
  }

  public void addInline(String contentId, String pathToInline)
      throws IOException, MessagingException {
    File file = new ClassPathResource(pathToInline).getFile();
    FileSystemResource fsr = new FileSystemResource(file);

    messageHelper.addInline(contentId, fsr);
  }

  public void addInline(String contentId, DataSource dataSource) throws MessagingException {
    messageHelper.addInline(contentId, dataSource);
  }

  public void send() {
    mailSender.send(message);
  }
}
