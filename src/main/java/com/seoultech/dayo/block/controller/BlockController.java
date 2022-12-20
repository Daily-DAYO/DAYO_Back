package com.seoultech.dayo.block.controller;

import com.seoultech.dayo.block.service.BlockService;
import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/block")
public class BlockController {

  private final MemberService memberService;
  private final BlockService blockService;

  @PostMapping
  public ResponseEntity<Void> blockMember(@ApiIgnore @LoginUser String memberId,
      @RequestBody BlockRequest.CreateDto request) {

    Member member = memberService.findMemberById(memberId);
    Member target = memberService.findMemberById(request.getMemberId());

    blockService.blockMember(member, target);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}
