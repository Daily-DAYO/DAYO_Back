package com.seoultech.dayo.folder.controller.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
public class EditFolderRequest {

  @NotNull
  private Long folderId;

  private String name;

  private String subheading;

  private String privacy;

  private MultipartFile thumbnailImage;

  private Boolean isFileChange;

}
