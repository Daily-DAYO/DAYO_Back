package com.seoultech.dayo.hashtag.controller;

import com.seoultech.dayo.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping("/{tag}")
    public void find(@PathVariable String tag) {

        hashtagService.findHashtag(tag);

    }

}
