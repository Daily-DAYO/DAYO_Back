package com.seoultech.dayo.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seoultech.dayo.config.jwt.TokenDto;
import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.exception.NotExistFollowerException;
import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.folder.repository.FolderRepository;
import com.seoultech.dayo.folder.service.FolderService;
import com.seoultech.dayo.follow.service.FollowService;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.repository.ImageRepository;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.controller.dto.request.MemberOAuthRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberProfileUpdateRequest;
import com.seoultech.dayo.member.controller.dto.response.MemberInfoResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberMyProfileResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberOAuthResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberOtherProfileResponse;
import com.seoultech.dayo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  private final FolderService folderService;
  private final ImageService imageService;
  private final TokenProvider tokenProvider;
  private final RestTemplate restTemplate;
  private final FollowService followService;

  public MemberOAuthResponse kakaoApi(MemberOAuthRequest request) {

    String apiUrl = "https://kapi.kakao.com/v2/user/me";
    String responseBody = get(apiUrl, request.getAccessToken());

    JsonParser parser = new JsonParser();
    JsonElement element = parser.parse(responseBody);

    JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
    JsonObject profile = kakaoAccount.getAsJsonObject().get("profile").getAsJsonObject();

    String name = profile.getAsJsonObject().get("nickname").getAsString();
    String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

    Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);

    Member member;
    if (memberOptional.isPresent()) {
      member = memberOptional.get();
    } else {
      Image profileImage = imageService.findDefaultProfileImage();
      member = memberRepository.save(new Member(name, email, profileImage));
      member.addFolder(folderService.createDefaultFolder());
    }

    TokenDto token = tokenProvider.generateToken(member.getId());
    return MemberOAuthResponse.from(token);
  }

  @Transactional(readOnly = true)
  public MemberInfoResponse memberInfo(String memberId) {
    Member member = findMemberById(memberId);
    return MemberInfoResponse.from(member);
  }

  @Transactional(readOnly = true)
  public MemberMyProfileResponse myProfile(String memberId) {
    Member member = findMemberById(memberId);
    return MemberMyProfileResponse.from(member);
  }

  @Transactional(readOnly = true)
  public MemberOtherProfileResponse otherProfile(String memberId, String otherId) {
    boolean isFollow = followService.isFollow(memberId, otherId);
    Member member = findMemberById(otherId);
    return MemberOtherProfileResponse.from(member, isFollow);
  }

  public void profileUpdate(String memberId, MemberProfileUpdateRequest request)
      throws IOException {

    Member member = findMemberById(memberId);

      if (StringUtils.hasText(request.getNickname())) {
          member.setNickname(request.getNickname());
      }
    if (request.getProfileImg() != null) {
      Image image = imageService.storeFile(request.getProfileImg());
      member.setProfileImg(image);
    }

  }

  public Member findMemberById(String memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(NotExistMemberException::new);
  }

  public Member findFollowerById(String memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(NotExistFollowerException::new);
  }

  private String get(String apiUrl, String accessToken) {

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + accessToken);

      HttpEntity entity = new HttpEntity(headers);
      ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity,
          String.class);

      return response.getBody();
    } catch (HttpStatusCodeException e) {
      throw new RuntimeException("API 요청과 응답 실패", e);
    }
  }
}