package com.custom.stream.model.nbagame;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.nodes.Element;

@ToString
@Getter
@Setter
public class Channel {
    private String name;
    private String speed;
    private String url;

    public Channel(Element element) {
        this.name = element.select(".username").text();
        this.speed = element.select(".label-purple").first().text();
        this.url = element.attr("data-stream-link");
    }
}
