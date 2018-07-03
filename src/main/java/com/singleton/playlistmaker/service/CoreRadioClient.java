package com.singleton.playlistmaker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoreRadioClient implements ServiceClient {

    private static final int START_PAGE = 1;
    private static final String PAGE_PATH = "/page/";

    @Value("${coreradio.host}")
    private String host;

    private RestTemplate restTemplate;

    public CoreRadioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getData(int page) {
        return restTemplate.getForObject(host + PAGE_PATH + page, String.class);
    }

    @Override
    public String getData() {
        return getData(START_PAGE);
    }
}
