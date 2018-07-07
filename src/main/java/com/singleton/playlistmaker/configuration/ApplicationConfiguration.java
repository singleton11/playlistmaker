package com.singleton.playlistmaker.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class ApplicationConfiguration {

    @Value("${deezer.accessToken}")
    private String authToken;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public UriComponentsBuilder uriComponentsBuilder() {
        return UriComponentsBuilder.newInstance().scheme("https").host("api.deezer.com").queryParam("access_token", authToken);
    }
}
