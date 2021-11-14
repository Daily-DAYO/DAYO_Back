package com.seoultech.dayo.domain.folder.controller.dto.request;


import com.seoultech.dayo.domain.folder.Folder;
import lombok.Getter;

@Getter
public class CreateFolderRequest {

    private String name;
    private String subheading;
    private String memberId;

    public Folder toEntity() {
        return new Folder(this.name, this.subheading);
    }

}
