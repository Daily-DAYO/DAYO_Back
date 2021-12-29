package com.seoultech.dayo.member;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


class MemberServiceTests {

    @Test
    public void RestTemplate_실패_테스트() {


        try {
            String apiUrl = "https://kapi.kakao.com/v2/user/me";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + "WMIZK9kknCq4TFYM_h1_Q250DOliR-ajqVfolwopyNkAAAF81sLhE");

            HttpEntity entity = new HttpEntity(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        } catch(HttpStatusCodeException e) {
            System.out.println(e);
        }
    }

}