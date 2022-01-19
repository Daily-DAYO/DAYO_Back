package com.seoultech.dayo.folder.controller.dto;

import com.seoultech.dayo.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyFolderDto {

    private Long folderId;

    private String name;

    private String subheading;

    private String thumbnailImage;

    private int postCount;

    public static MyFolderDto from(Folder folder) {
        return new MyFolderDto(folder.getId(), folder.getName(), folder.getSubheading(), folder.getThumbnailImage().getStoreFileName() ,folder.getPostCount());
    }

}
