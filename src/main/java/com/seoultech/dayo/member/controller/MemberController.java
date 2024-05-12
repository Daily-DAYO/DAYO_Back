package com.seoultech.dayo.member.controller;

import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.exception.ExistNicknameException;
import com.seoultech.dayo.exception.NotExistEmailException;
import com.seoultech.dayo.exception.dto.BadRequestFailResponse;
import com.seoultech.dayo.exception.dto.NotFoundFailResponse;
import com.seoultech.dayo.mail.MailService;
import com.seoultech.dayo.member.controller.dto.request.ChangePasswordRequest;
import com.seoultech.dayo.member.controller.dto.request.ChangeReceiveAlarmRequest;
import com.seoultech.dayo.member.controller.dto.request.CheckPasswordRequest;
import com.seoultech.dayo.member.controller.dto.request.DeviceTokenRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberOAuthRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberProfileUpdateRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberResignRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberSignInRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberSignUpRequest;
import com.seoultech.dayo.member.controller.dto.response.MemberAuthCodeResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberInfoResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberListResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberMyProfileResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberOAuthResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberOtherProfileResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberSignInResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberSignUpResponse;
import com.seoultech.dayo.member.controller.dto.response.ReceiveAlarmResponse;
import com.seoultech.dayo.member.controller.dto.response.RefreshTokenResponse;
import com.seoultech.dayo.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Member", description = "멤버 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberService memberService;
  private final MailService mailService;

  @Tag(name = "Member")
  @Operation(summary = "카카오 로그인", description = "카카오 로그인")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "카카오 로그인 성공", content = @Content(schema = @Schema(implementation = MemberOAuthResponse.class))),
      @ApiResponse(responseCode = "500", description = "카카오 api 오류")})
  @PostMapping("/kakaoOAuth")
  public ResponseEntity<MemberOAuthResponse> kakaoOAuth(@RequestBody MemberOAuthRequest request) {
    return ResponseEntity.ok()
        .body(memberService.kakaoApi(request));
  }

  @Tag(name = "Member")
  @Operation(summary = "멤버 정보 조회")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "멤버 조회 성공", content = @Content(schema = @Schema(implementation = MemberInfoResponse.class))),
      @ApiResponse(responseCode = "404", description = "멤버 조회 불가", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @GetMapping("/myInfo")
  public ResponseEntity<MemberInfoResponse> memberInfo(@ApiIgnore @LoginUser String memberId) {
    return ResponseEntity.ok()
        .body(memberService.memberInfo(memberId));
  }

  @Tag(name = "Member")
  @Operation(summary = "다른 멤버 정보 조회")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "다른 멤버 조회 성공", content = @Content(schema = @Schema(implementation = MemberOtherProfileResponse.class))),
      @ApiResponse(responseCode = "404", description = "멤버 조회 불가", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @GetMapping("/profile/other/{memberId}")
  public ResponseEntity<MemberOtherProfileResponse> otherProfile(
      @ApiIgnore @LoginUser String myMemberId,
      @PathVariable @Valid String memberId) {
    return ResponseEntity.ok()
        .body(memberService.otherProfile(myMemberId, memberId));
  }

  @Tag(name = "Member")
  @Operation(summary = "내 프로필 조회")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "내 프로필 조회 성공", content = @Content(schema = @Schema(implementation = MemberMyProfileResponse.class))),
      @ApiResponse(responseCode = "404", description = "멤버 조회 불가", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @GetMapping("/profile/my")
  public ResponseEntity<MemberMyProfileResponse> myProfile(@ApiIgnore @LoginUser String memberId) {
    return ResponseEntity.ok()
        .body(memberService.myProfile(memberId));
  }

  @Tag(name = "Member")
  @Operation(summary = "프로필 수정")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
      @ApiResponse(responseCode = "404", description = "멤버 조회 불가", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping("/update/profile")
  public ResponseEntity<Void> profileUpdate(@ApiIgnore @LoginUser String memberId,
      @ModelAttribute @Valid MemberProfileUpdateRequest request) throws IOException {
    memberService.profileUpdate(memberId, request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Tag(name = "Member")
  @Operation(summary = "이메일 중복 확인", description = "존재하면 200 존재하지 않으면 에러")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "중복되는 이메일이 존재하지 않음"),
      @ApiResponse(responseCode = "400", description = "중복되는 이메일 존재", content = @Content(schema = @Schema(implementation = BadRequestFailResponse.class)))})
  @GetMapping("/duplicate/email/{email}")
  public ResponseEntity<Void> duplicateEmail(@PathVariable @Email String email) {
    memberService.duplicateEmail(email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Tag(name = "Member")
  @Operation(summary = "멤버 회원 가입 절차 - 이메일 인증")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "이메일 전송 완료", content = @Content(schema = @Schema(implementation = MemberAuthCodeResponse.class))),
      @ApiResponse(responseCode = "500", description = "이메일 전송 에러")})
  @GetMapping("/signUp/{email}")
  public ResponseEntity<MemberAuthCodeResponse> signUpMember(
      @PathVariable @Email String email) {
    String authCode = mailService.sendAuthMail(email);
    return ResponseEntity.ok()
        .body(MemberAuthCodeResponse.from(authCode));
  }

  @Tag(name = "Member")
  @Operation(summary = "멤버 회원 가입 절차 - 정보 등록")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "회원가입 완료", content = @Content(schema = @Schema(implementation = MemberSignUpResponse.class)))
  })
  @PostMapping("/signUp")
  public ResponseEntity<MemberSignUpResponse> signUpMember(
      @ModelAttribute MemberSignUpRequest request) throws IOException {
    return ResponseEntity.ok()
        .body(memberService.signUp(request));
  }

  @Tag(name = "Member")
  @Operation(summary = "로그인")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "로그인 완료", content = @Content(schema = @Schema(implementation = MemberSignInResponse.class))),
      @ApiResponse(responseCode = "404", description = "멤버 조회 불가", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping("/signIn")
  public ResponseEntity<MemberSignInResponse> signInMember(
      @RequestBody MemberSignInRequest request) {
    return ResponseEntity.ok()
        .body(memberService.signIn(request));
  }

  @Tag(name = "Member")
  @Operation(summary = "리프레시 토큰 발급")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "리프레시 토큰 발급", content = @Content(schema = @Schema(implementation = RefreshTokenResponse.class)))
  })
  @GetMapping("/refresh")
  public ResponseEntity<RefreshTokenResponse> refreshToken(@ApiIgnore @LoginUser String memberId) {
    return ResponseEntity.ok()
        .body(memberService.refreshAccessToken(memberId));
  }

  @Tag(name = "Member")
  @Operation(summary = "디바이스 토큰 저장")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "디바이스 토큰 저장 완료"),
      @ApiResponse(responseCode = "404", description = "멤버 조회 불가", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))
  })
  @PostMapping
  public ResponseEntity<Void> deviceToken(@RequestBody DeviceTokenRequest request,
      @ApiIgnore @LoginUser String memberId) {
    memberService.setDeviceToken(memberId, request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Tag(name = "Member")
  @Operation(summary = "이메일 존재 확인")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "존재하는 이메일"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 이메일", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))
  })
  @GetMapping("/search/{email}")
  public ResponseEntity<Void> searchEmail(@PathVariable @Email String email) {
    if (memberService.existMemberByEmail(email)) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    throw new NotExistEmailException();
  }

  @Tag(name = "Member")
  @Operation(summary = "이메일 존재 확인")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "존재하는 이메일"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 이메일", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))
  })
  @GetMapping("/search/code/{email}")
  public ResponseEntity<MemberAuthCodeResponse> searchPassword(@PathVariable @Email String email) {
    if (memberService.existMemberByEmail(email)) {
      String authCode = mailService.sendAuthMail(email);
      return ResponseEntity.ok()
          .body(MemberAuthCodeResponse.from(authCode));
    }
    throw new NotExistEmailException();
  }

  @Tag(name = "Member")
  @Operation(summary = "비밀번호 변경")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "존재하는 이메일"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 이메일", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))
  })
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
      @ApiIgnore @LoginUser String memberId) {
    memberService.resign(memberId, request);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/checkPassword")
  public ResponseEntity<Void> checkPassword(@RequestBody CheckPasswordRequest request,
      @ApiIgnore @LoginUser String memberId) {
    memberService.checkPassword(request, memberId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/changeReceiveAlarm")
  public ResponseEntity<Void> changeReceiveAlarm(@RequestBody ChangeReceiveAlarmRequest request,
      @ApiIgnore @LoginUser String memberId) {
    memberService.changeReceiveAlarm(request, memberId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/receiveAlarm")
  public ResponseEntity<ReceiveAlarmResponse> showReceiveAlarm(
      @ApiIgnore @LoginUser String memberId) {
    return ResponseEntity.ok()
        .body(memberService.showReceiveAlarm(memberId));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@ApiIgnore @LoginUser String memberId) {
    memberService.logout(memberId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/check")
  public ResponseEntity<Void> checkNickname(@RequestParam("nickname") String nickname) {
    if (memberService.existNickname(nickname)) {
      throw new ExistNicknameException();
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/block")
  public ResponseEntity<MemberListResponse> blockMemberList(@ApiIgnore @LoginUser String memberId) {
    return ResponseEntity.ok().body(memberService.blockMember(memberId));
  }

}
