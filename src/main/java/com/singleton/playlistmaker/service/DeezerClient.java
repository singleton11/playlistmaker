package com.singleton.playlistmaker.service;

import com.singleton.playlistmaker.domain.DeezerResponse;
import com.singleton.playlistmaker.domain.Playlist;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
public class DeezerClient {

    private RestTemplate restTemplate;
    private UriComponentsBuilder uriComponentsBuilder;

    Playlist createPlaylist(String title) {
        String url = uriComponentsBuilder.cloneBuilder().path("/user/me/playlists").queryParam("title", title).build().toUriString();
        return restTemplate.postForObject(url, HttpEntity.EMPTY, Playlist.class);
    }

    private List<Playlist> getPlaylists() {
        String url = uriComponentsBuilder.cloneBuilder().path("/user/me/playlists").build().toUriString();
        DeezerResponse<Playlist> response = restTemplate.exchange(url,
                                                                  HttpMethod.GET,
                                                                  HttpEntity.EMPTY,
                                                                  new ParameterizedTypeReference<DeezerResponse<Playlist>>() {
                                                                  }).getBody();
        return response.getData();
    }

    private void deletePlaylist(long id) {
        String url = uriComponentsBuilder.cloneBuilder()
                                         .path("/playlist/" + id)
                                         .queryParam("request_method", "delete")
                                         .build()
                                         .toUriString();
        restTemplate.delete(url);
    }

    void deletePlaylist(String title) {
        getPlaylists().stream().filter(playlist -> playlist.getTitle().equals(title)).findFirst().ifPresent(playlist -> deletePlaylist(playlist.getId()));
    }
}
