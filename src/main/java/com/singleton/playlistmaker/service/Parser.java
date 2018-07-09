package com.singleton.playlistmaker.service;

import com.singleton.playlistmaker.domain.Album;
import java.util.List;

public interface Parser {

    List<Album> parse(String data);
}
