package com.seoultech.dayo.folder.controller.dto.response;

import com.seoultech.dayo.folder.controller.dto.FolderDetailDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetailFolderResponse {

  private int count;

  private boolean last;

  private List<FolderDetailDto> data;

  public static DetailFolderResponse from(List<FolderDetailDto> collect, boolean last) {
    return new DetailFolderResponse(collect.size(), last, collect);
  }

}
