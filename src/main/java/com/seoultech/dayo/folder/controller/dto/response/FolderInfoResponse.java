package com.seoultech.dayo.folder.controller.dto.response;

import com.seoultech.dayo.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FolderInfoResponse {

  private String name;

  private String subheading;

  private String thumbnailImage;

  private String privacy;

  private String memberId;

  private int postCount;

  public static FolderInfoResponse from(Folder folder) {
    return new FolderInfoResponse(folder.getName(), folder.getSubheading(),
        folder.getThumbnailImage().getStoreFileName(), folder.getPrivacy().name(),
        folder.getMember().getId(), folder.getPostCount());
  }
}
