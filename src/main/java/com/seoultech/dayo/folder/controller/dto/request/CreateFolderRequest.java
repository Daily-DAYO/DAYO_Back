package com.seoultech.dayo.folder.controller.dto.request;


import com.seoultech.dayo.Image.Image;
import com.seoultech.dayo.folder.Folder;
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
