package com.seoultech.dayo.domain.post.controller.dto;


import com.seoultech.dayo.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {

    private String thumbnailImage;

    private String nickname;

    private Integer heartCount;

    private Integer commentCount;

    public static PostDto from(Post post) {
        return new PostDto(post.getThumbnailImage(), post.getMember().getName(), post.getHearts().size(), post.getComments().size());
    }

}
