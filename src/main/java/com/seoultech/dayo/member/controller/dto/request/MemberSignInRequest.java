package com.seoultech.dayo.member.controller.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MemberSignInRequest {

  @Email
  private String email;

  @NotBlank
  private String password;

}
