package com.seoultech.dayo.alarm.controller;

import com.seoultech.dayo.alarm.controller.dto.response.ListAllAlarmResponse;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmController {

  private final AlarmService alarmService;
  private final MemberService memberService;

  @GetMapping
  public ResponseEntity<ListAllAlarmResponse> listAllAlarm(@LoginUser String memberId) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(alarmService.listAll(member));
  }

  @PostMapping("/{alarmId}")
  public ResponseEntity<Void> isCheckAlarm(@PathVariable Long alarmId) {
    alarmService.isCheckAlarm(alarmId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
