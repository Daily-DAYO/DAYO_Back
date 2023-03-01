package com.seoultech.dayo.folder.controller.dto.response;

import com.seoultech.dayo.folder.controller.dto.FolderDto;
import com.seoultech.dayo.folder.controller.dto.MyFolderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllMyFolderResponse {

  private int count;

  private boolean last;

  private List<MyFolderDto> data;

  public static ListAllMyFolderResponse from(List<MyFolderDto> folderDtos, boolean last) {
    return new ListAllMyFolderResponse(folderDtos.size(), last, folderDtos);
  }

}
