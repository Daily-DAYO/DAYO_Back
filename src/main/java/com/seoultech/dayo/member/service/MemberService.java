package com.seoultech.dayo.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.block.Block;
import com.seoultech.dayo.bookmark.service.BookmarkService;
import com.seoultech.dayo.comment.service.CommentService;
import com.seoultech.dayo.config.jwt.TokenDto;
import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.exception.ExistEmailException;
import com.seoultech.dayo.exception.IncorrectPasswordException;
import com.seoultech.dayo.exception.NotExistEmailException;
import com.seoultech.dayo.exception.NotExistFollowerException;
import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.folder.service.FolderService;
import com.seoultech.dayo.follow.service.FollowService;
import com.seoultech.dayo.heart.service.HeartService;
import com.seoultech.dayo.image.Category;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.inquiry.service.InquiryService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.controller.dto.request.ChangePasswordRequest;
import com.seoultech.dayo.member.controller.dto.request.ChangeReceiveAlarmRequest;
import com.seoultech.dayo.member.controller.dto.request.CheckPasswordRequest;
import com.seoultech.dayo.member.controller.dto.request.DeviceTokenRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberOAuthRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberProfileUpdateRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberResignRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberSignInRequest;
import com.seoultech.dayo.member.controller.dto.request.MemberSignUpRequest;
import com.seoultech.dayo.member.controller.dto.response.MemberInfoResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberListResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberMyProfileResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberOAuthResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberOtherProfileResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberSignInResponse;
import com.seoultech.dayo.member.controller.dto.response.MemberSignUpResponse;
import com.seoultech.dayo.member.controller.dto.response.ReceiveAlarmResponse;
import com.seoultech.dayo.member.controller.dto.response.RefreshTokenResponse;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.service.PostService;
import com.seoultech.dayo.report.service.ReportService;
import com.seoultech.dayo.resign.Resign;
import com.seoultech.dayo.resign.repository.ResignRepository;
import com.seoultech.dayo.search.service.SearchService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

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
  private final PasswordEncoder passwordEncoder;
  private final ResignRepository resignRepository;
  private final BookmarkService bookmarkService;
  private final AlarmService alarmService;
  private final CommentService commentService;
  private final HeartService heartService;
  private final InquiryService inquiryService;
  private final ReportService reportService;
  private final SearchService searchService;
  private final PostService postService;

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

  public MemberSignInResponse signIn(MemberSignInRequest request) {

    Member member = memberRepository.findMemberByEmail(
            request.getEmail())
        .orElseThrow(NotExistMemberException::new);

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if (encoder.matches(request.getPassword(), member.getPassword())) {
      TokenDto token = tokenProvider.generateToken(member.getId());
      return MemberSignInResponse.from(token);
    }

    throw new IncorrectPasswordException();
  }

  public void setDeviceToken(String memberId, DeviceTokenRequest request) {
    Member member = findMemberById(memberId);
    member.setDeviceToken(request.getDeviceToken());
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
    int size = (int) member.getPosts()
        .stream()
        .filter(post -> post.getPrivacy() != Privacy.ONLY_ME)
        .count();
    return MemberOtherProfileResponse.from(member, isFollow, size);
  }

  public void profileUpdate(String memberId, MemberProfileUpdateRequest request)
      throws IOException {

    Member member = findMemberById(memberId);

    if (StringUtils.hasText(request.getNickname())) {
      if (member.getNickname() == null || !member.getNickname().equals(request.getNickname())) {
        member.setNickname(request.getNickname());
      }
    }
    if (request.getProfileImg() != null) {
      Image image = imageService.storeFile(request.getProfileImg(), Category.PROFILE);
      imageService.resizeFile(image.getStoreFileName(), 17, 17);
      imageService.resizeFile(image.getStoreFileName(), 37, 37);
      imageService.resizeFile(image.getStoreFileName(), 45, 45);
      member.setProfileImg(image);
    }
    if (request.isOnBasicProfileImg()) {
      Image image = imageService.findDefaultProfileImage();
      member.setProfileImg(image);
    }

  }

  public RefreshTokenResponse refreshAccessToken(String memberId) {
    TokenDto tokenDto = tokenProvider.refreshAccessToken(memberId);
    return RefreshTokenResponse.from(tokenDto.getAccessToken());
  }

  public MemberSignUpResponse signUp(MemberSignUpRequest request) throws IOException {

    String password = passwordEncoder.encode(request.getPassword());

    Image image;
    if (request.getProfileImg() != null) {
      image = imageService.storeFile(request.getProfileImg(), Category.PROFILE);
      imageService.resizeFile(image.getStoreFileName(), 17, 17);
      imageService.resizeFile(image.getStoreFileName(), 37, 37);
      imageService.resizeFile(image.getStoreFileName(), 45, 45);
    } else {
      image = imageService.findDefaultProfileImage();
    }

    Member savedMember = memberRepository.save(
        new Member(request.getNickname(), request.getEmail(), password,
            image));
    savedMember.addFolder(folderService.createDefaultFolder());

    return MemberSignUpResponse.from(savedMember);
  }

  public boolean existMemberByEmail(String email) {
    return memberRepository.existsMemberByEmail(email);
  }

  public Member findMemberById(String memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(NotExistMemberException::new);
  }

  public Member findFollowerById(String memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(NotExistFollowerException::new);
  }

  public void duplicateEmail(String email) {
    memberRepository.findMemberByEmail(email)
        .ifPresent(member -> {
          throw new ExistEmailException();
        });
  }

  public void changePassword(ChangePasswordRequest request) {
    Member member = memberRepository.findMemberByEmail(request.getEmail())
        .orElseThrow(NotExistEmailException::new);
    member.setPassword(passwordEncoder.encode(request.getPassword()));
  }

  public void resign(String memberId, MemberResignRequest request) {
    Member member = findMemberById(memberId);

    // TODO: refactoring
    bookmarkService.deleteAllByMember(member);
    alarmService.deleteAllByMember(member);
    commentService.deleteAllByMember(member);
    folderService.deleteAllByMember(member);
    followService.deleteAllByMember(member);
    heartService.deleteAllByMember(member);
    inquiryService.deleteAllByMember(member);
    reportService.deleteAllByMember(member);
    searchService.deleteAllByMember(member);
    postService.deleteAllByMember(member);

    memberRepository.deleteById(memberId);
    resignRepository.save(new Resign(request.getContent()));
  }

  public void checkPassword(CheckPasswordRequest request, String memberId) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(NotExistMemberException::new);

    if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
      throw new IncorrectPasswordException();
    }
  }

  public void changeReceiveAlarm(ChangeReceiveAlarmRequest request, String memberId) {
    Member member = findMemberById(memberId);
    member.setOnReceiveAlarm(request.getOnReceiveAlarm());
  }

  public ReceiveAlarmResponse showReceiveAlarm(String memberId) {
    Member member = findMemberById(memberId);
    return ReceiveAlarmResponse.from(member);
  }

  public void logout(String memberId) {
    Member member = findMemberById(memberId);
    member.setDeviceToken(null);
  }

  public boolean existNickname(String nickname) {
    return memberRepository.existsMemberByNickname(nickname);
  }

  public MemberListResponse blockMember(String memberId) {

    Member member = findMemberById(memberId);
    List<Block> blockList = member.getBlockList();

    List<BlockMember> collect = blockList.stream()
        .map(block -> BlockMember.from(block.getTarget()))
        .collect(Collectors.toList());

    return MemberListResponse.from(collect);
  }

  public List<Member> findMemberListByNicknameLike(String nickname) {
    return memberRepository.findMembersByNicknameContaining(nickname);
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
