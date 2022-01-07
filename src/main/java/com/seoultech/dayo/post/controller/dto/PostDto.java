package com.seoultech.dayo.post.controller.dto;


import com.seoultech.dayo.post.Post;
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
        return new PostDto(post.getId(), post.getThumbnailImage(), post.getMember().getName(), post.getMember().getProfileImg().getStoreFileName(), post.getHearts().size(), post.getComments().size());
    }

}
