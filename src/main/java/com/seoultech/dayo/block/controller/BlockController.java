package com.seoultech.dayo.block.controller;

import com.seoultech.dayo.block.service.BlockService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Block", description = "차단 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/block")
public class BlockController {

  private final MemberService memberService;
  private final BlockService blockService;

  @Tag(name = "Block")
  @Operation(summary = "사용자 차단하기", description = "memberId를 넣어 해당 사용자를 차단합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "CREATED"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping
  public ResponseEntity<Void> blockMember(@ApiIgnore @LoginUser String memberId,
      @RequestBody BlockRequest.CreateDto request) {

    Member member = memberService.findMemberById(memberId);
    Member target = memberService.findMemberById(request.getMemberId());

    blockService.blockMember(member, target);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Tag(name = "Block")
  @Operation(summary = "사용자 차단 취소", description = "memberId를 넣어 해당 사용자 차단을 취소합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping("/cancel")
  public ResponseEntity<Void> blockCancel(@ApiIgnore @LoginUser String memberId,
      @RequestBody BlockRequest.CancelDto request) {

    Member member = memberService.findMemberById(memberId);
    Member target = memberService.findMemberById(request.getMemberId());

    blockService.cancel(member, target);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
