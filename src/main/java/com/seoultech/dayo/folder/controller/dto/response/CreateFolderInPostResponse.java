package com.seoultech.dayo.folder.controller.dto.response;

import com.seoultech.dayo.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFolderInPostResponse {

    private Long folderId;

    public static CreateFolderInPostResponse from(Folder folder) {
        return new CreateFolderInPostResponse(folder.getId());
    }

}
