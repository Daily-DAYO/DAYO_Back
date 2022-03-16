package com.seoultech.dayo.member.controller.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MemberSignUpRequest {

  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String nickname;

  private MultipartFile profileImg;

}
