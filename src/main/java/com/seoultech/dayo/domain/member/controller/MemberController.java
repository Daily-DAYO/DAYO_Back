package com.seoultech.dayo.domain.member.controller;

import com.seoultech.dayo.domain.member.service.MemberService;
import com.seoultech.dayo.domain.member.controller.dto.request.MemberOAuthRequest;
import com.seoultech.dayo.domain.member.controller.dto.response.MemberOAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/kakaoOAuth")
    public ResponseEntity<MemberOAuthResponse> kakaoOAuth(@RequestBody MemberOAuthRequest request) {
        return ResponseEntity.ok()
                .body(memberService.kakaoApi(request));
    }


}
