package com.seoultech.dayo.post.controller.dto.request;


import com.seoultech.dayo.exception.NotExistPostCategoryException;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class CreatePostRequest {

  @NotBlank
  private String contents;

  @NotNull
  private Long folderId;

  @NotNull
  private String category;

  private List<String> tags;

  @NotNull
  private List<MultipartFile> files;

  public Post toEntity(Member member, List<Image> images) {

    if (Category.find(category)) {
      return Post.builder()
          .member(member)
          .contents(contents)
          .thumbnailImage(images.get(0).getStoreFileName())
          .category(Category.valueOf(category))
          .images(images)
          .build();
    } else {
      throw new NotExistPostCategoryException();
    }


  }

}
