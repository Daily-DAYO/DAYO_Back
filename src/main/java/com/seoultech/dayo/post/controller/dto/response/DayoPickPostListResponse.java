package com.seoultech.dayo.post.controller.dto.response;


import com.seoultech.dayo.post.controller.dto.DayoPick;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DayoPickPostListResponse implements Serializable {

  private int count;

  private List<DayoPick> data;

  public static DayoPickPostListResponse from(List<DayoPick> collect) {
    return new DayoPickPostListResponse(collect.size(), collect);
  }

}
