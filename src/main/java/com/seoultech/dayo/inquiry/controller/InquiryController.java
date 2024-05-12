package com.seoultech.dayo.inquiry.controller;

import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.exception.dto.NotFoundFailResponse;
import com.seoultech.dayo.inquiry.controller.dto.request.CreateInquiryRequest;
import com.seoultech.dayo.inquiry.service.InquiryService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Inquiry", description = "문의 API")
@RestController
@RequestMapping("/api/v1/inquiry")
@RequiredArgsConstructor
public class InquiryController {

  private final InquiryService inquiryService;
  private final MemberService memberService;

  @Tag(name = "Inquiry")
  @Operation(summary = "문의 신청", description = "문의 신청합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "문의 신청 성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping
  public ResponseEntity<Void> create(@ApiIgnore @LoginUser String memberId,
      CreateInquiryRequest request) {
    Member member = memberService.findMemberById(memberId);

    inquiryService.create(request, member);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
