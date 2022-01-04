package com.seoultech.dayo.follow.controller;


import com.seoultech.dayo.follow.controller.dto.request.CreateFollowRequest;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowUpRequest;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowResponse;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowUpResponse;
import com.seoultech.dayo.follow.controller.dto.response.ListAllFollowerResponse;
import com.seoultech.dayo.follow.controller.dto.response.ListAllFollowingResponse;
import com.seoultech.dayo.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<CreateFollowResponse> createFollow(@RequestBody @Valid CreateFollowRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(followService.createFollow(request));
    }

    @PostMapping("/up")
    public ResponseEntity<CreateFollowUpResponse> createFollowUp(@RequestBody @Valid CreateFollowUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(followService.createFollowUp(request));
    }

    @GetMapping("/follower/{memberId}")
    public ResponseEntity<ListAllFollowerResponse> listAllFollower(@PathVariable @Valid String memberId) {
        return ResponseEntity.ok()
                .body(followService.listAllFollowers(memberId));
    }

    @GetMapping("/following/{memberId}")
    public ResponseEntity<ListAllFollowingResponse> listAllFollowing(@PathVariable @Valid String memberId) {
        return ResponseEntity.ok()
                .body(followService.listAllFollowings(memberId));
    }


}
