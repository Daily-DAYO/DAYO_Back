package com.seoultech.dayo.folder.controller.dto.request;


import com.seoultech.dayo.exception.NotExistFolderPrivacyException;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CreateFolderRequest {

  @NotNull
  private String name;

  private String subheading;

  @NotNull
  @Setter
  private String privacy;

  private MultipartFile thumbnailImage;

  public Folder toEntity(Image image) {
    if (Privacy.find(privacy)) {
      return new Folder(this.name, this.subheading, Privacy.valueOf(this.privacy), image);
    } else {
      throw new NotExistFolderPrivacyException();
    }
  }

}
