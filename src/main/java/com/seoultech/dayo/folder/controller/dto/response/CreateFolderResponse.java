package com.seoultech.dayo.folder.controller.dto.response;

import com.seoultech.dayo.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateFolderResponse {

    private Long folderId;

    public static CreateFolderResponse from(Folder folder) {
        return new CreateFolderResponse(folder.getId());
    }
}
