package com.seoultech.dayo.post.controller.dto.response;


import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.response.ListAllCommentResponse;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.postHashtag.PostHashtag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class DetailPostResponse {

  private String nickname;

  private String profileImg;

  private LocalDateTime createDateTime;

  private Category category;

  private List<String> images;

  private String contents;

  private List<String> hashtags;

  private String privacy;

  private String folderName;

  private boolean isHeart;

  private int heartCount;

  private boolean isBookmark;

  public static DetailPostResponse from(Post post, boolean isHeart, boolean isBookmark) {
    List<String> collectImages = post.getImages().stream()
        .map(Image::getStoreFileName)
        .collect(toList());

    List<String> collectHashtags = post.getPostHashtags().stream()
        .map(postHashtag -> postHashtag.getHashtag().getTag())
        .collect(toList());

    return new DetailPostResponse(post.getMember().getNickname(),
        post.getMember().getProfileImg().getStoreFileName(),
        post.getCreatedDate(),
        post.getCategory(),
        collectImages,
        post.getContents(),
        collectHashtags,
        post.getPrivacy().name(),
        post.getFolder().getName(),
        isHeart,
        post.getHearts().size(),
        isBookmark
    );
  }

}
