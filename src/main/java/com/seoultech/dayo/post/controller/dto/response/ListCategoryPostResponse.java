package com.seoultech.dayo.post.controller.dto.response;

import com.seoultech.dayo.post.controller.dto.PostDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ListCategoryPostResponse {

  private int count;

  private boolean last;

  private List<PostDto> data;

  public static ListCategoryPostResponse from(List<PostDto> data, boolean last) {
    return new ListCategoryPostResponse(data.size(), last, data);
  }

}
