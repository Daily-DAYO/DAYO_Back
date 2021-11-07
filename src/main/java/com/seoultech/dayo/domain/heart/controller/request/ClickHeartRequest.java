package com.seoultech.dayo.domain.heart.controller.request;

import com.seoultech.dayo.domain.heart.Heart;
import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.post.Post;
import lombok.Getter;

@Getter

public class ClickHeartRequest {

    private String memberId;

    private Long postId;

    public Heart toEntity(Member member, Post post) {
        Heart heart = new Heart(member);
        heart.addPost(post);
        return heart;

    }
}
