package com.custom.stream.model.gimy;

import com.custom.stream.provider.Configs;
import lombok.*;
import org.jsoup.nodes.Element;

@ToString
@Getter
@Setter
public class GimyVideo {
    private String name;
    private String url;
    private String img;

    public GimyVideo(Element element) {
        this.name = element.attr(Configs.GIMY_SEARCH_RESULT_TITLE.toString());
        this.url = Configs.GIMY_BASE + element.attr(Configs.GIMY_SEARCH_RESULT_URL.toString());
        this.img = element.attr(Configs.GIMY_SEARCH_RESULT_IMG.toString());
    }

}
