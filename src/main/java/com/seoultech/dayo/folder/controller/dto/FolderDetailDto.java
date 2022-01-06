package com.seoultech.dayo.folder.controller.dto;

import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FolderDetailDto {

    private Long postId;

    private String thumbnailImage;

    public static FolderDetailDto from(Post post) {
        return new FolderDetailDto(post.getId(), post.getThumbnailImage());
    }

}
