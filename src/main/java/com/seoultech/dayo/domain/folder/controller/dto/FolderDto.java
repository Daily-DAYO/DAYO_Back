package com.seoultech.dayo.domain.folder.controller.dto;

import com.seoultech.dayo.domain.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FolderDto {

    private String name;

    private String subheading;

    private int postCount;

    public static FolderDto from(Folder folder) {
        return new FolderDto(folder.getName(), folder.getSubheading(), folder.getPosts().size());
    }

}
