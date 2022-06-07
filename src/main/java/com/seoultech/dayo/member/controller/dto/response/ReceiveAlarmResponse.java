package com.seoultech.dayo.member.controller.dto.response;

import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReceiveAlarmResponse {

  private Boolean onReceiveAlarm;

  public static ReceiveAlarmResponse from(Member member) {
    return new ReceiveAlarmResponse(member.getOnReceiveAlarm());
  }

}
