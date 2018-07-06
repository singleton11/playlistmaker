package com.singleton.playlistmaker.service;

import com.singleton.playlistmaker.domain.Album;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlbumProvider {

    private ServiceClient client;
    private Parser parser;

    List<Album> provide(int number) {
        int provided = 0;
        int page = 1;
        List<Album> albums = new ArrayList<>();
        while (provided < number) {
            String data = client.getData(page);
            albums.addAll(parser.parse(data));
            provided += albums.size();
        }
        return albums.stream().limit(number).collect(Collectors.toList());
    }
}
