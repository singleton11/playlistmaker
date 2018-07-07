package com.singleton.playlistmaker.domain;

import java.util.List;
import lombok.Data;

@Data
public class DeezerResponse<T> {

    private List<T> data;

}
