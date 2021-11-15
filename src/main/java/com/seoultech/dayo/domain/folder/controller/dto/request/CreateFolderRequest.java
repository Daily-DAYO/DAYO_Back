package com.seoultech.dayo.domain.folder.controller.dto.request;


import com.seoultech.dayo.domain.Image.Image;
import com.seoultech.dayo.domain.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFolderRequest {

    private String name;
    private String subheading;
    private String memberId;

    public Folder toEntity(Image image) {
        return new Folder(this.name, this.subheading, image);
    }

}
