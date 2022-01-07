package com.seoultech.dayo.member.controller;

import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.member.controller.dto.request.MemberProfileUpdateRequest;
import com.seoultech.dayo.member.controller.dto.response.MemberInfoResponse;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.member.controller.dto.request.MemberOAuthRequest;
import com.seoultech.dayo.member.controller.dto.response.MemberOAuthResponse;
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
    private final TokenProvider tokenProvider;

    @PostMapping("/kakaoOAuth")
    public ResponseEntity<MemberOAuthResponse> kakaoOAuth(@RequestBody MemberOAuthRequest request) {
        return ResponseEntity.ok()
                .body(memberService.kakaoApi(request));
    }

    @GetMapping("/myInfo")
    public ResponseEntity<MemberInfoResponse> memberInfo(HttpServletRequest servletRequest) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.ok()
                .body(memberService.memberInfo(memberId));
    }

    @PostMapping("/profile")
    public ResponseEntity<Void> profileUpdate(HttpServletRequest servletRequest, @ModelAttribute @Valid MemberProfileUpdateRequest request) throws IOException {
        String memberId = getDataInToken(servletRequest);
        memberService.profileUpdate(memberId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getDataInToken(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization").substring(7);
        return tokenProvider.getDataFromToken(token);
    }

}
