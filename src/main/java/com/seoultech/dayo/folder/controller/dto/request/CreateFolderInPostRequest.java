package com.seoultech.dayo.folder.controller.dto.request;

import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
public class CreateFolderInPostRequest {

  @NotNull
  private String name;

  private String privacy;

  public Folder toEntity(Image image) {
    //TODO: 소개글 문구 추가
    return new Folder(this.name, "폴더 소개를 적어주세요", Privacy.valueOf(this.privacy), image);
  }

}
