package com.seoultech.dayo.domain.folder.controller.dto.response;

import com.seoultech.dayo.domain.folder.controller.dto.FolderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllFolderResponse {

    private int count;

    private List<FolderDto> data;

    public static ListAllFolderResponse from(List<FolderDto> folderDtos) {
        return new ListAllFolderResponse(folderDtos.size(), folderDtos);
    }

}
