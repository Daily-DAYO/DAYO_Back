package com.seoultech.dayo.folder.controller.dto.response;

import com.seoultech.dayo.folder.controller.dto.FolderDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListAllFolderResponse {

  private int count;

  private boolean last;

  private List<FolderDto> data;

  public static ListAllFolderResponse from(List<FolderDto> folderDtos, boolean last) {
    return new ListAllFolderResponse(folderDtos.size(), last, folderDtos);
  }

}
