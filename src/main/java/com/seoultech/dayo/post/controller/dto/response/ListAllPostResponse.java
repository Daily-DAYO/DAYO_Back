package com.seoultech.dayo.post.controller.dto.response;


import com.seoultech.dayo.post.controller.dto.PostDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListAllPostResponse {

  private int count;

  private boolean last;

  private List<PostDto> data;

  public static ListAllPostResponse from(List<PostDto> data, boolean last) {
    return new ListAllPostResponse(data.size(), last, data);
  }

}
