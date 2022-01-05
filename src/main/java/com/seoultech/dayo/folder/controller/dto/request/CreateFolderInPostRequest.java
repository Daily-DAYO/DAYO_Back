package com.seoultech.dayo.folder.controller.dto.request;

import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.Privacy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CreateFolderInPostRequest {

    @NotNull
    private String name;

    private String privacy;

    public Folder toEntity() {
        return new Folder(name, Privacy.valueOf(privacy));
    }

}
