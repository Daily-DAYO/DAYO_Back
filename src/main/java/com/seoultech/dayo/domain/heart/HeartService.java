package com.seoultech.dayo.domain.heart;

import com.seoultech.dayo.domain.heart.controller.response.ClickHeartResponse;
import com.seoultech.dayo.domain.member.MemberRepository;
import com.seoultech.dayo.domain.post.repository.PostRepository;
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
