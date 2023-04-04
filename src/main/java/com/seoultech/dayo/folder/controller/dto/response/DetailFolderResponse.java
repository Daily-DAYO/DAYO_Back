package com.seoultech.dayo.folder.controller.dto.response;

import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.controller.dto.FolderDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DetailFolderResponse {

  private int count;

  private String name;

  private String subheading;

  private String thumbnailImage;

  private String privacy;

  private String memberId;

  private Boolean last;

  private List<FolderDetailDto> data;

  public static DetailFolderResponse from(Folder folder, List<FolderDetailDto> collect,
      Boolean last) {
    return new DetailFolderResponse(collect.size(), folder.getName(), folder.getSubheading(),
        folder.getThumbnailImage().getStoreFileName(), folder.getPrivacy().name(),
        folder.getMember().getId(), last, collect);
  }

}
