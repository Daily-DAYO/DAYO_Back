package com.seoultech.dayo.folder.controller.dto.response;

import com.seoultech.dayo.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditFolderResponse {

    private Long folderId;

    public static EditFolderResponse from(Folder folder) {
        return new EditFolderResponse(folder.getId());
    }
}
