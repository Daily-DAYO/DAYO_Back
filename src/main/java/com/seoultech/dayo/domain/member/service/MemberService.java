package com.seoultech.dayo.domain.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.member.controller.dto.request.MemberOAuthRequest;
import com.seoultech.dayo.domain.member.controller.dto.response.MemberOAuthResponse;
import com.seoultech.dayo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public MemberOAuthResponse kakaoApi(MemberOAuthRequest request) {

        String apiUrl = "https://kapi.kakao.com/v2/user/me";
        String responseBody = get(apiUrl, request.getAccessToken());

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(responseBody);

        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
        JsonObject profile = kakaoAccount.getAsJsonObject().get("profile").getAsJsonObject();

        String name = profile.getAsJsonObject().get("nickname").getAsString();
        String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
        String profileImage = profile.getAsJsonObject().get("profile_image_url").getAsString();

        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);

        Member member = memberOptional.orElseGet(() ->
                memberRepository.save(new Member(name,email,profileImage))
        );

        return MemberOAuthResponse.from(member);
    }

    private String get(String apiUrl, String accessToken) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        }
    }
}
