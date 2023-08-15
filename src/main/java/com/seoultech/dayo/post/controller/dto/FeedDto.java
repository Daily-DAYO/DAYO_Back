package com.seoultech.dayo.post.controller.dto;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.postHashtag.PostHashtag;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedDto {

  private Long id;

  private List<String> images;

  private String memberId;

  private String nickname;

  private String userProfileImage;

  private Integer heartCount;

  private Integer commentCount;

  private boolean isHeart;

  private boolean isBookmark;

  private String contents;

  private String category;

  private LocalDateTime createTime;

  private List<String> hashtags;

  public static FeedDto from(Post post, boolean isHeart, boolean isBookmark) {
    List<Hashtag> collect = post.getPostHashtags().stream()
        .map(PostHashtag::getHashtag)
        .collect(Collectors.toList());
    List<String> hashtags = collect.stream()
        .map(Hashtag::getTag)
        .collect(Collectors.toList());
    List<String> images = post.getImages().stream()
        .map(Image::getStoreFileName)
        .collect(Collectors.toList());

    return new FeedDto(post.getId(), images,
        post.getMember().getId(), post.getMember().getNickname(),
        post.getMember().getProfileImg().getResizeFileName(37, 37),
        post.getHeartCount(),
        post.getCommentCount(),
        isHeart,
        isBookmark,
        post.getContents(),
        post.getCategory().toString(),
        post.getCreatedDate().withNano(0),
        hashtags
    );
  }

}
