package com.seoultech.dayo.follow.controller;


import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.exception.dto.NotFoundFailResponse;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowRequest;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowUpRequest;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowResponse;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowUpResponse;
import com.seoultech.dayo.follow.controller.dto.response.ListAllFollowerResponse;
import com.seoultech.dayo.follow.controller.dto.response.ListAllFollowingResponse;
import com.seoultech.dayo.follow.controller.dto.response.ListAllMyFollowerResponse;
import com.seoultech.dayo.follow.controller.dto.response.ListAllMyFollowingResponse;
import com.seoultech.dayo.follow.service.FollowService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

  private final FollowService followService;
  private final MemberService memberService;

  @Tag(name = "Follow")
  @Operation(summary = "팔로우 신청", description = "상대방에게 팔로우 신청 합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "팔로우 신청 성공", content = @Content(schema = @Schema(implementation = CreateFollowResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping
  public ResponseEntity<CreateFollowResponse> createFollow(@ApiIgnore @LoginUser String memberId,
      @RequestBody @Valid CreateFollowRequest request) {
    Member member = memberService.findMemberById(memberId);
    Member follower = memberService.findFollowerById(request.getFollowerId());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(followService.createFollow(member, follower, request));
  }

  @Tag(name = "Follow")
  @Operation(summary = "맞팔로우 신청", description = "상대방에게 맞팔로우 신청 합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "맞팔로우 신청 성공", content = @Content(schema = @Schema(implementation = CreateFollowUpResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping("/up")
  public ResponseEntity<CreateFollowUpResponse> createFollowUp(
      @ApiIgnore @LoginUser String memberId,
      @RequestBody @Valid CreateFollowUpRequest request) {
    Member member = memberService.findMemberById(memberId);
    Member follower = memberService.findFollowerById(request.getFollowerId());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(followService.createFollowUp(member, follower, request));
  }

  @Tag(name = "Follow")
  @Operation(summary = "맞팔로우 취소", description = "팔로우 취소 합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "팔로우 삭제 성공", content = @Content(schema = @Schema(implementation = CreateFollowUpResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping("/delete/{followerId}")
  public ResponseEntity<Void> deleteFollow(@ApiIgnore @LoginUser String memberId,
      @PathVariable String followerId) {
    Member member = memberService.findMemberById(memberId);
    Member follower = memberService.findFollowerById(followerId);

    followService.deleteFollow(member, follower);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/follower/my")
  public ResponseEntity<ListAllMyFollowerResponse> listAllMyFollower(
      @ApiIgnore @LoginUser String memberId) {
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(followService.listAllMyFollowers(member));
  }

  @GetMapping("/following/my")
  public ResponseEntity<ListAllMyFollowingResponse> listAllMyFollowing(
      @ApiIgnore @LoginUser String memberId) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(followService.listAllMyFollowings(member));
  }

  @GetMapping("/follower/list/{memberId}")
  public ResponseEntity<ListAllFollowerResponse> listAllFollower(
      @ApiIgnore @LoginUser String myMemberId,
      @PathVariable @Valid String memberId) {
    Member me = memberService.findMemberById(myMemberId);
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(followService.listAllFollowers(me, member));
  }

  @GetMapping("/following/list/{memberId}")
  public ResponseEntity<ListAllFollowingResponse> listAllFollowing(
      @ApiIgnore @LoginUser String myMemberId,
      @PathVariable @Valid String memberId) {
    Member me = memberService.findMemberById(myMemberId);
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(followService.listAllFollowings(me, member));
  }

}
