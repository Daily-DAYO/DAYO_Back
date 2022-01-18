package com.seoultech.dayo.heart.controller.dto.request;

import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
public class CreateHeartRequest {

    @NotNull
    private Long postId;

    public Heart toEntity(Member member, Post post) {
        Heart heart = new Heart(new Heart.Key(member.getId(), post.getId()), member);
        heart.addPost(post);
        return heart;
    }
}
