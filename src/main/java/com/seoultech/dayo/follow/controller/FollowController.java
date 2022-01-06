package com.seoultech.dayo.follow.controller;


import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowRequest;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowUpRequest;
import com.seoultech.dayo.follow.controller.dto.response.*;
import com.seoultech.dayo.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<CreateFollowResponse> createFollow(HttpServletRequest servletRequest, @RequestBody @Valid CreateFollowRequest request) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(followService.createFollow(memberId, request));
    }

    @PostMapping("/up")
    public ResponseEntity<CreateFollowUpResponse> createFollowUp(HttpServletRequest servletRequest, @RequestBody @Valid CreateFollowUpRequest request) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(followService.createFollowUp(memberId, request));
    }

    @PostMapping("/delete/{followerId}")
    public ResponseEntity<Void> deleteFollow(HttpServletRequest servletRequest, @PathVariable String followerId) {
        String memberId = getDataInToken(servletRequest);
        followService.deleteFollow(memberId, followerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/follower/my")
    public ResponseEntity<ListAllMyFollowerResponse> listAllMyFollower(HttpServletRequest servletRequest) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.ok()
                .body(followService.listAllMyFollowers(memberId));
    }

    @GetMapping("/following/my")
    public ResponseEntity<ListAllMyFollowingResponse> listAllMyFollowing(HttpServletRequest servletRequest) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.ok()
                .body(followService.listAllMyFollowings(memberId));
    }

    @GetMapping("/follower/list/{memberId}")
    public ResponseEntity<ListAllFollowerResponse> listAllFollower(@PathVariable @Valid String memberId) {
        return ResponseEntity.ok()
                .body(followService.listAllFollowers(memberId));
    }

    @GetMapping("/following/list/{memberId}")
    public ResponseEntity<ListAllFollowingResponse> listAllFollowing(@PathVariable @Valid String memberId) {
        return ResponseEntity.ok()
                .body(followService.listAllFollowings(memberId));
    }

    private String getDataInToken(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization").substring(7);
        return tokenProvider.getDataFromToken(token);
    }

}
