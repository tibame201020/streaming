package com.custom.stream.model.gimy;

import lombok.*;

import static com.custom.stream.provider.GimyParser.BASE_URL;

@ToString
@Getter
@Setter
public class GimyVideo {
    private String name;
    private String url;
    private String img;

    public GimyVideo(String videoStr) {
        this.name = videoStr.substring(videoStr.indexOf("title=") + 7, videoStr.indexOf("data-original") - 2);
        this.url = BASE_URL + videoStr.substring(videoStr.indexOf("/vod"), videoStr.indexOf("\" title="));
        this.img = videoStr.substring(videoStr.indexOf("original=") + 10, videoStr.length() - 1);
    }

}
