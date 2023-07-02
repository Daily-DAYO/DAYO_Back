package com.seoultech.dayo.folder.controller.dto;

import com.seoultech.dayo.post.Post;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FolderDetailDto {

  private Long postId;

  private String thumbnailImage;

  private String createDate;

  public static FolderDetailDto from(Post post) {
    return new FolderDetailDto(post.getId(), post.getThumbnailImage().getStoreFileName(),
        post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")));
  }

}
