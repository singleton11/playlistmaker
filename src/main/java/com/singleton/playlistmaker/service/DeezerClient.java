package com.singleton.playlistmaker.service;

import com.singleton.playlistmaker.domain.DeezerResponse;
import com.singleton.playlistmaker.domain.musicapi.Album;
import com.singleton.playlistmaker.domain.musicapi.Playlist;
import com.singleton.playlistmaker.domain.musicapi.Track;
import java.util.List;
import java.util.stream.Collectors;
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

    List<Track> search(String query) {
        String url = uriComponentsBuilder.cloneBuilder()
                                         .path("search")
                                         .queryParam("q", query)
                                         .build()
                                         .toUriString();

        DeezerResponse<Track> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<DeezerResponse<Track>>() {
        }).getBody();

        return response.getData();
    }

    Album getAlbumInfo(long albumId) {
        String url = uriComponentsBuilder.cloneBuilder().path("/album/" + albumId).build().toUriString();
        return restTemplate.getForObject(url, Album.class);
    }

    void addTracksToPlaylist(Playlist playlist, List<Track> tracks) {
        String url = uriComponentsBuilder.cloneBuilder()
                                         .path("/playlist/" + playlist.getId() + "/tracks")
                                         .queryParam("songs",
                                                     tracks.stream().map(track -> String.valueOf(track.getId())).collect(Collectors.joining(",")))
                                         .build()
                                         .toUriString();
        restTemplate.postForObject(url, HttpEntity.EMPTY, String.class);
    }
}
