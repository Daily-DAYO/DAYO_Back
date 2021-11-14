package com.seoultech.dayo.domain.folder.controller.dto.response;

import com.seoultech.dayo.domain.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateFolderResponse {

    private Long id;

    public static CreateFolderResponse from(Folder folder) {
        return new CreateFolderResponse(folder.getId());
    }
}
