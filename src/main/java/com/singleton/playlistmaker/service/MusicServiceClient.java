package com.singleton.playlistmaker.service;

import com.singleton.playlistmaker.domain.musicapi.Album;
import com.singleton.playlistmaker.domain.musicapi.Playlist;
import com.singleton.playlistmaker.domain.musicapi.Track;
import java.util.List;

public interface MusicServiceClient {

    Playlist createPlaylist(String title);

    void deletePlaylist(String title);

    Album getAlbumInfo(long albumId);

    void addTracksToPlaylist(Playlist playlist, List<Track> tracks);

    List<Track> search(String query);
}
