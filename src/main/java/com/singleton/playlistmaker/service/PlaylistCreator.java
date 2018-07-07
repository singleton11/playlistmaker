package com.singleton.playlistmaker.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PlaylistCreator {

    private static final String PLAYLIST_TITLE = "Core radio";

    private DeezerClient musicClient;

    public void create() {
        log.info("Removing previous playlist: " + PLAYLIST_TITLE);
        musicClient.deletePlaylist(PLAYLIST_TITLE);

        log.info("Creating new playlist: " + PLAYLIST_TITLE);
        musicClient.createPlaylist(PLAYLIST_TITLE);
    }
}
