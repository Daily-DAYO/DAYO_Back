package com.seoultech.dayo.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(50)    // 최대 커넥션 수
                .setMaxConnPerRoute(20) // 각 호스트당 커넥션 풀에 생성가능한 커넥션 수
                .build();

        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }

}
