package com.seoultech.dayo.member.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MemberProfileUpdateRequest {

    private String nickname;

    private MultipartFile profileImg;


}
