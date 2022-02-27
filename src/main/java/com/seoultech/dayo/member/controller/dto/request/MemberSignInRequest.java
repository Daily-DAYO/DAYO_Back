package com.seoultech.dayo.member.controller.dto.request;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignInRequest {

  @Email
  private String email;

  private String password;

}
