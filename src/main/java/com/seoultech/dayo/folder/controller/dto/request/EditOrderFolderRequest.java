package com.seoultech.dayo.folder.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditOrderFolderRequest {

    private List<EditOrderDto> data;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditOrderDto {

        private Long folderId;
        private int orderIndex;

    }

}
