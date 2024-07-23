package com.seoultech.dayo.bookmark.controller.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteBookmarkRequest {

    private List<Long> postIdList;

}
