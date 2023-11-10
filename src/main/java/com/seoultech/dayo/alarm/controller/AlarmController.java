package com.seoultech.dayo.alarm.controller;

import com.seoultech.dayo.alarm.controller.dto.response.ListAllAlarmResponse;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.exception.dto.NotFoundFailResponse;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Alarm", description = "알람 API")
@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmController {

  private final AlarmService alarmService;
  private final MemberService memberService;

  @Tag(name = "Alarm")
  @Operation(summary = "사용자 알람 전체 조회(페이징)")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "알람 조회 성공", content = @Content(schema = @Schema(implementation = ListAllAlarmResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @GetMapping
  public ResponseEntity<ListAllAlarmResponse> listAllAlarm(@ApiIgnore @LoginUser String memberId,
      @RequestParam(value = "end") String end) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(alarmService.listAll(member, Long.valueOf(end)));
  }

  @Tag(name = "Alarm")
  @Operation(summary = "알람 조회 여부 확인", description = "읽지 않은 알람을 읽은 상태로 표시")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping("/{alarmId}")
  public ResponseEntity<Void> isCheckAlarm(@PathVariable Long alarmId) {
    alarmService.isCheckAlarm(alarmId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
