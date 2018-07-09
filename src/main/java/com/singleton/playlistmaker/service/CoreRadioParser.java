package com.singleton.playlistmaker.service;

import com.singleton.playlistmaker.domain.Album;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class CoreRadioParser implements Parser {

    final private Pattern genrePattern = Pattern.compile("Genre: (.*) Country");
    final private Pattern titlePattern = Pattern.compile("([\\w\\s-]*) \\[?.*\\(");

    @Override
    public List<Album> parse(String data) {
        Document doc = Jsoup.parse(data);
        Elements elements = doc.getElementsByClass("main-news");
        return elements.stream().map(this::getAlbum).collect(Collectors.toList());
    }

    private Album getAlbum(Element element) {
        String genreString = element.getElementsByClass("tcarusel-item-descr2").get(0).text();
        String titleString = element.getElementsByClass("tcarusel-item-title").get(0).text();
        return Album.builder().genre(extractByPattern(genreString, genrePattern)).title(extractByPattern(titleString, titlePattern)).build();
    }

    private String extractByPattern(String source, Pattern pattern) {
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
