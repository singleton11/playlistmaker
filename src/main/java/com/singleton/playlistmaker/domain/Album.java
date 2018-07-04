package com.singleton.playlistmaker.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Album {

    private String title;
    private String genre;
}
