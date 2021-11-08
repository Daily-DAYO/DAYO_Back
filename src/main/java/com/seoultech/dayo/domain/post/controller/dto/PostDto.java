package com.seoultech.dayo.domain.post.controller.dto;


import com.seoultech.dayo.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {

    private Long id;

    private String thumbnailImage;

    private String nickname;

    private String userProfileImage;

    private Integer heartCount;

    private Integer commentCount;

    public static PostDto from(Post post) {
        return new PostDto(post.getId(), post.getThumbnailImage(), post.getMember().getName(), post.getMember().getProfileImg(), post.getHearts().size(), post.getComments().size());
    }

}
