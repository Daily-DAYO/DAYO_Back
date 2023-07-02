package com.seoultech.dayo.heart.controller;

import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.heart.controller.dto.request.CreateHeartRequest;
import com.seoultech.dayo.heart.controller.dto.response.CreateHeartResponse;
import com.seoultech.dayo.heart.controller.dto.response.DeleteHeartResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllHeartPostResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllMyHeartPostResponse;
import com.seoultech.dayo.heart.service.HeartService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
  public ResponseEntity<DeleteHeartResponse> deleteHeart(@ApiIgnore @LoginUser String memberId,
      @PathVariable Long postId) {
    Member member = memberService.findMemberById(memberId);
    Post post = postService.findPostById(postId);

    return ResponseEntity.ok()
        .body(heartService.deleteHeart(member, post));
  }

  @GetMapping("/list/{memberId}")
  public ResponseEntity<ListAllHeartPostResponse> listAllHeartPost(
      @PathVariable @Valid String memberId,
      @RequestParam(value = "end") String end) {
    Member member = memberService.findMemberById(memberId);
    Set<String> blockList = postService.getBlockList(member);

    return ResponseEntity.ok()
        .body(heartService.listAllHeartPost(member, blockList, Long.valueOf(end)));
  }

  @GetMapping("/list")
  public ResponseEntity<ListAllMyHeartPostResponse> listAllMyHeartPost(
      @ApiIgnore @LoginUser String memberId,
      @RequestParam(value = "end") String end) {
    Member member = memberService.findMemberById(memberId);
    Set<String> blockList = postService.getBlockList(member);

    return ResponseEntity.ok()
        .body(heartService.listAllMyHeartPost(member, blockList, Long.valueOf(end)));
  }

}
