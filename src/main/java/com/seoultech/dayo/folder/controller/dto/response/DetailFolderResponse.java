package com.seoultech.dayo.folder.controller.dto.response;

import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.controller.dto.FolderDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DetailFolderResponse {

    private int count;

    private String name;

    private String subheading;

    private List<FolderDetailDto> data;

    public static DetailFolderResponse from(Folder folder, List<FolderDetailDto> collect) {
        return new DetailFolderResponse(collect.size(), folder.getName(), folder.getSubheading(), collect);
    }

}
