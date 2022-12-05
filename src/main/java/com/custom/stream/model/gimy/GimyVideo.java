package com.custom.stream.model.gimy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.nodes.Element;

import static com.custom.stream.provider.Configs.*;

@ToString
@Getter
@Setter
public class GimyVideo {
    private String name;
    private String url;
    private String img;

    public GimyVideo(Element element) {
        this.name = element.attr(GIMY_SEARCH_RESULT_TITLE.toString());
        this.url = GIMY_BASE + element.attr(GIMY_SEARCH_RESULT_URL.toString());
        this.img = element.attr(GIMY_SEARCH_RESULT_IMG.toString());
    }

}
