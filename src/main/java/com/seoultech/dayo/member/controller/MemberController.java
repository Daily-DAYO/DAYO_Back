package com.seoultech.dayo.member.controller;

import com.seoultech.dayo.mail.MailService;
import com.seoultech.dayo.member.controller.dto.request.ChangePasswordRequest;
import com.seoultech.dayo.member.controller.dto.request.ChangeReceiveAlarmRequest;
import com.seoultech.dayo.member.controller.dto.request.CheckPasswordRequest;
import com.seoultech.dayo.member.controller.dto.request.DeviceTokenRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberProfileUpdateRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberSignInRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberSignUpRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberResignRequest;
import com.seoultech.dayo.member.controller.dto.response.MemberAuthCodeResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberInfoResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberMyProfileResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberOtherProfileResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberSignInResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberSignUpResponse;
import com.seoultech.dayo.member.controller.dto.response.ReceiveAlarmResponse;
import com.seoultech.dayo.member.controller.dto.response.RefreshTokenResponse;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.member.controller.dto.request.MemberOAuthRequest;
import com.seoultech.dayo.member.controller.dto.response.MemberOAuthResponse;
import javax.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberService memberService;
  private final MailService mailService;

  @PostMapping("/kakaoOAuth")
  public ResponseEntity<MemberOAuthResponse> kakaoOAuth(@RequestBody MemberOAuthRequest request) {
    return ResponseEntity.ok()
        .body(memberService.kakaoApi(request));
  }

  @GetMapping("/myInfo")
  public ResponseEntity<MemberInfoResponse> memberInfo(HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();
    return ResponseEntity.ok()
        .body(memberService.memberInfo(memberId));
  }

  @GetMapping("/profile/other/{memberId}")
  public ResponseEntity<MemberOtherProfileResponse> otherProfile(HttpServletRequest servletRequest,
      @PathVariable @Valid String memberId) {
    String myMemberId = servletRequest.getAttribute("memberId").toString();
    return ResponseEntity.ok()
        .body(memberService.otherProfile(myMemberId, memberId));
  }

  @GetMapping("/profile/my")
  public ResponseEntity<MemberMyProfileResponse> myProfile(HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();
    return ResponseEntity.ok()
        .body(memberService.myProfile(memberId));
  }

  @PostMapping("/update/profile")
  public ResponseEntity<Void> profileUpdate(HttpServletRequest servletRequest,
      @ModelAttribute @Valid MemberProfileUpdateRequest request) throws IOException {
    String memberId = servletRequest.getAttribute("memberId").toString();
    memberService.profileUpdate(memberId, request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/duplicate/email/{email}")
  public ResponseEntity<Void> duplicateEmail(@PathVariable @Email String email) {
    memberService.duplicateEmail(email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/signUp/{email}")
  public ResponseEntity<MemberAuthCodeResponse> signUpMember(
      @PathVariable @Email String email) {

    String authCode = mailService.sendAuthMail(email);

    return ResponseEntity.ok()
        .body(MemberAuthCodeResponse.from(authCode));
  }

  @PostMapping("/signUp")
  public ResponseEntity<MemberSignUpResponse> signUpMember(
      @ModelAttribute MemberSignUpRequest request) throws IOException {
    return ResponseEntity.ok()
        .body(memberService.signUp(request));
  }

  @PostMapping("/signIn")
  public ResponseEntity<MemberSignInResponse> signInMember(
      @RequestBody MemberSignInRequest request) {
    return ResponseEntity.ok()
        .body(memberService.signIn(request));
  }

  @GetMapping("/refresh")
  public ResponseEntity<RefreshTokenResponse> refreshToken(HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();
    return ResponseEntity.ok()
        .body(memberService.refreshAccessToken(memberId));

  }

  @PostMapping
  public ResponseEntity<Void> deviceToken(@RequestBody DeviceTokenRequest request,
      HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();
    memberService.setDeviceToken(memberId, request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/search/{email}")
  public ResponseEntity<Void> searchPassword(@PathVariable @Email String email) {
    if (memberService.existMemberByEmail(email)) {
      mailService.sendAuthMail(email);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    throw new IllegalStateException("존재하지 않는 이메일입니다.");
  }

  @PostMapping("/changePassword")
  public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
    memberService.changePassword(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/setting/changePassword")
  public ResponseEntity<Void> changePasswordInSetting(@RequestBody ChangePasswordRequest request) {
    memberService.changePassword(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/resign")
  public ResponseEntity<Void> resign(MemberResignRequest request,
      HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();

    memberService.resign(memberId, request);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/checkPassword")
  public ResponseEntity<Void> checkPassword(@RequestBody CheckPasswordRequest request,
      HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();

    memberService.checkPassword(request, memberId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/changeReceiveAlarm")
  public ResponseEntity<Void> changeReceiveAlarm(@RequestBody ChangeReceiveAlarmRequest request,
      HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();

    memberService.changeReceiveAlarm(request, memberId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/receiveAlarm")
  public ResponseEntity<ReceiveAlarmResponse> showReceiveAlarm(HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();

    return ResponseEntity.ok()
        .body(memberService.showReceiveAlarm(memberId));
  }

}
