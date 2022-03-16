package com.seoultech.dayo.post.controller.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class EditPostRequest {

  private Long folderId;

  private List<String> hashtags;

  private String category;

  private String contents;

  private String privacy;

}
