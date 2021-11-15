package com.seoultech.dayo.domain.post.controller.dto.response;

import com.seoultech.dayo.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostResponse {

    private Long id;

    public static CreatePostResponse from(Post post) {
        return new CreatePostResponse(post.getId());
    }

}
