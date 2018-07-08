package com.singleton.playlistmaker.domain.musicapi;

import com.singleton.playlistmaker.domain.DeezerResponse;
import lombok.Data;

@Data
public class Album {

    private long id;
    private String name;
    private DeezerResponse<Track> tracks;
}
