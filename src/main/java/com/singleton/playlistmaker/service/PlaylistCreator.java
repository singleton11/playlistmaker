package com.singleton.playlistmaker.service;

import com.singleton.playlistmaker.domain.Album;
import com.singleton.playlistmaker.domain.musicapi.Playlist;
import com.singleton.playlistmaker.domain.musicapi.Track;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PlaylistCreator {

    private static final String PLAYLIST_TITLE = "Core radio";
    private static final int ALBUM_COUNT = 100;

    private MusicServiceClient musicClient;
    private Provider provider;

    public void create() {
        log.info("Removing previous playlist: " + PLAYLIST_TITLE);
        musicClient.deletePlaylist(PLAYLIST_TITLE);

        log.info("Creating new playlist: " + PLAYLIST_TITLE);
        Playlist playlist = musicClient.createPlaylist(PLAYLIST_TITLE);

        log.info("Provide " + ALBUM_COUNT + " albums");
        List<Album> albums = provider.provide(ALBUM_COUNT);

        int trackCounter = 0;

        for (Album album : albums) {
            log.info("Search " + album.getTitle() + " in music catalog");
            AtomicLong albumId = new AtomicLong();
            musicClient.search(album.getTitle()).stream().findFirst().ifPresent(song -> albumId.set(song.getAlbum().getId()));
            log.info("Album ID is " + albumId);
            if (albumId.get() != 0) {
                log.info("Getting list of tracks of album " + albumId);
                List<Track> tracks = musicClient.getAlbumInfo(albumId.get()).getTracks().getData();
                log.info("Adding tracks of album to playlist " + playlist.getId());
                musicClient.addTracksToPlaylist(playlist, tracks);
                log.info("Added " + tracks.size() + " tracks to playlist " + playlist.getId());
                trackCounter += tracks.size();
            }

        }
        log.info(trackCounter + " added to playlist " + playlist.getId());
    }
}
