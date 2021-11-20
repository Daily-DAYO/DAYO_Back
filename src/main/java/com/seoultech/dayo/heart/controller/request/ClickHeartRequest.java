package com.seoultech.dayo.heart.controller.request;

import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
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
