package com.seoultech.dayo.alarm.controller;

import com.seoultech.dayo.alarm.controller.dto.response.ListAllAlarmResponse;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmController {

  private final AlarmService alarmService;
  private final MemberService memberService;

  @GetMapping
  public ResponseEntity<ListAllAlarmResponse> listAllAlarm(HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(alarmService.listAll(member));
  }

}
