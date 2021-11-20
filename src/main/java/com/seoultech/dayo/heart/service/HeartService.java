package com.seoultech.dayo.heart.service;

import com.seoultech.dayo.heart.controller.response.ClickHeartResponse;
import com.seoultech.dayo.heart.repository.HeartRepository;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final MemberRepository memberRepository;
    private final HeartRepository heartRepository;
    private final PostRepository postRepository;

    public ClickHeartResponse clickHeart() {
        return null;
    }


}
