package com.seoultech.dayo.post.controller.dto.response;


import com.seoultech.dayo.post.controller.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllPostResponse {

    private int count;

    private List<PostDto> data;

}
