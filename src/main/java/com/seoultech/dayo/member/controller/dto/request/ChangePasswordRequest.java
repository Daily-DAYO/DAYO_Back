package com.seoultech.dayo.member.controller.dto.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {

  private String email;

  private String password;

}
