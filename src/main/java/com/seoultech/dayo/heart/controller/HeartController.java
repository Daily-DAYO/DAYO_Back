package com.seoultech.dayo.heart.controller;

import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.heart.controller.dto.request.CreateHeartRequest;
import com.seoultech.dayo.heart.controller.dto.response.CreateHeartResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllHeartPostResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllMyHeartPostResponse;
import com.seoultech.dayo.heart.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/heart")
public class HeartController {

    private final HeartService heartService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<CreateHeartResponse> createHeart(HttpServletRequest servletRequest, @RequestBody @Valid CreateHeartRequest request) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(heartService.createHeart(memberId, request));
    }

    @PostMapping("/delete/{postId}")
    public ResponseEntity<Void> deleteHeart(HttpServletRequest servletRequest, Long postId) {
        String memberId = getDataInToken(servletRequest);
        heartService.deleteHeart(memberId, postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list/{memberId}")
    public ResponseEntity<ListAllHeartPostResponse> listAllHeartPost(@PathVariable @Valid String memberId) {
        return ResponseEntity.ok()
                .body(heartService.listAllHeartPost(memberId));
    }

    @GetMapping("/list")
    public ResponseEntity<ListAllMyHeartPostResponse> listAllMyHeartPost(HttpServletRequest servletRequest) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.ok()
                .body(heartService.listAllMyHeartPost(memberId));
    }

    private String getDataInToken(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization").substring(7);
        return tokenProvider.getDataFromToken(token);
    }

}
