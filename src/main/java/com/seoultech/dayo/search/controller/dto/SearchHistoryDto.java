package com.seoultech.dayo.search.controller.dto;

import com.seoultech.dayo.search.Search;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchHistoryDto {

  private Long searchId;

  private String history;

  public static SearchHistoryDto from(Search search) {
    return new SearchHistoryDto(search.getId(), search.getContent());
  }

}
