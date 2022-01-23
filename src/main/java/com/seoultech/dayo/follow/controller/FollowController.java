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
        String token = tokenProvider.getTokenInHeader(servletRequest);
        String memberId = tokenProvider.getDataFromToken(token);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(followService.createFollow(memberId, request));
    }

    @PostMapping("/up")
    public ResponseEntity<CreateFollowUpResponse> createFollowUp(HttpServletRequest servletRequest, @RequestBody @Valid CreateFollowUpRequest request) {
        String token = tokenProvider.getTokenInHeader(servletRequest);
        String memberId = tokenProvider.getDataFromToken(token);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(followService.createFollowUp(memberId, request));
    }

    @PostMapping("/delete/{followerId}")
    public ResponseEntity<Void> deleteFollow(HttpServletRequest servletRequest, @PathVariable String followerId) {
        String token = tokenProvider.getTokenInHeader(servletRequest);
        String memberId = tokenProvider.getDataFromToken(token);
        followService.deleteFollow(memberId, followerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/follower/my")
    public ResponseEntity<ListAllMyFollowerResponse> listAllMyFollower(HttpServletRequest servletRequest) {
        String token = tokenProvider.getTokenInHeader(servletRequest);
        String memberId = tokenProvider.getDataFromToken(token);
        return ResponseEntity.ok()
                .body(followService.listAllMyFollowers(memberId));
    }

    @GetMapping("/following/my")
    public ResponseEntity<ListAllMyFollowingResponse> listAllMyFollowing(HttpServletRequest servletRequest) {
        String token = tokenProvider.getTokenInHeader(servletRequest);
        String memberId = tokenProvider.getDataFromToken(token);
        return ResponseEntity.ok()
                .body(followService.listAllMyFollowings(memberId));
    }

    @GetMapping("/follower/list/{memberId}")
    public ResponseEntity<ListAllFollowerResponse> listAllFollower(HttpServletRequest servletRequest, @PathVariable @Valid String memberId) {
        String token = tokenProvider.getTokenInHeader(servletRequest);
        tokenProvider.getDataFromToken(token);
        return ResponseEntity.ok()
                .body(followService.listAllFollowers(memberId));
    }

    @GetMapping("/following/list/{memberId}")
    public ResponseEntity<ListAllFollowingResponse> listAllFollowing(@PathVariable @Valid String memberId) {
        return ResponseEntity.ok()
                .body(followService.listAllFollowings(memberId));
    }

}
