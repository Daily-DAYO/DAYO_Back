package com.seoultech.dayo.heart.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.heart.controller.dto.request.CreateHeartRequest;
import com.seoultech.dayo.heart.controller.dto.response.CreateHeartResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllHeartPostResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllMyHeartPostResponse;
import com.seoultech.dayo.heart.service.HeartService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/heart")
public class HeartController {

  private final HeartService heartService;
  private final MemberService memberService;
  private final PostService postService;

  @PostMapping
  public ResponseEntity<CreateHeartResponse> createHeart(@ApiIgnore @LoginUser String memberId,
      @RequestBody @Valid CreateHeartRequest request) {
    Member member = memberService.findMemberById(memberId);
    Post post = postService.findPostById(request.getPostId());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(heartService.createHeart(member, post, request));
  }

  @PostMapping("/delete/{postId}")
  public ResponseEntity<Void> deleteHeart(@ApiIgnore @LoginUser String memberId,
      @PathVariable Long postId) {
    Member member = memberService.findMemberById(memberId);
    Post post = postService.findPostById(postId);

    heartService.deleteHeart(member, post);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/list/{memberId}")
  public ResponseEntity<ListAllHeartPostResponse> listAllHeartPost(
      @PathVariable @Valid String memberId) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(heartService.listAllHeartPost(member));
  }

  @GetMapping("/list")
  public ResponseEntity<ListAllMyHeartPostResponse> listAllMyHeartPost(
      @ApiIgnore @LoginUser String memberId) {
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(heartService.listAllMyHeartPost(member));
  }

}
