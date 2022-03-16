package com.seoultech.dayo.hashtag.controller;

import com.seoultech.dayo.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping("/elasticsearch")
    public void find(@RequestParam String tag) {

//        hashtagService.findHashtag(tag);

    }

}
