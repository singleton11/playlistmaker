package com.singleton.playlistmaker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoreRadioClient {

    private static final int START_PAGE = 1;
    private static final String PAGE_PATH = "/page/";

    private static final String host = "http://coreradio.ru";

    private RestTemplate restTemplate;

    CoreRadioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    String getData(int page) {
        return restTemplate.getForObject(host + PAGE_PATH + page, String.class);
    }

    String getData() {
        return getData(START_PAGE);
    }
}
