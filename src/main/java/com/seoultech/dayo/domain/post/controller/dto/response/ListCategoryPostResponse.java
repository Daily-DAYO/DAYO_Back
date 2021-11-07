package com.seoultech.dayo.domain.post.controller.dto.response;

import com.seoultech.dayo.domain.post.controller.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class ListCategoryPostResponse {

    private int count;

    private List<PostDto> data;

}
