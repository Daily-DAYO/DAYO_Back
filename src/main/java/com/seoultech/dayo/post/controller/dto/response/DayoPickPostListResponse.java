package com.seoultech.dayo.post.controller.dto.response;


import com.seoultech.dayo.post.controller.dto.DayoPickDto;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DayoPickPostListResponse implements Serializable {

  private int count;

  private List<DayoPickDto> data;

  public static DayoPickPostListResponse from(List<DayoPickDto> collect) {
    return new DayoPickPostListResponse(collect.size(), collect);
  }

}
