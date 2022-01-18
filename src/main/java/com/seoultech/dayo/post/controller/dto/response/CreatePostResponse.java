package com.seoultech.dayo.post.controller.dto.response;

import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CreatePostResponse {

    private Long id;

    public static CreatePostResponse from(Post post) {
        return new CreatePostResponse(post.getId());
    }

}
