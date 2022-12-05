package com.custom.stream.service.impl;

import com.custom.stream.model.gimy.*;
import com.custom.stream.provider.RestTemplateProvider;
import com.custom.stream.provider.Configs;
import com.custom.stream.service.Tube;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TubeImpl implements Tube {


    @Override
    public SearchResult getListByGimy(String keyword) {
        String url = Configs.GIMY_SEARCH_BASE + keyword;
        Document doc = RestTemplateProvider.htmlToDoc(url, HttpMethod.GET, false);
        List<GimyVideo> gimyVideos = new ArrayList<>();
        Elements elements = doc.select(Configs.GIMY_SEARCH_RESULT_QUERY_SELECTOR.toString());
        elements.forEach(element -> gimyVideos.add(new GimyVideo(element)));
        return new SearchResult(
                url,
                Configs.GIMY_BASE +
                doc.select(Configs.GIMY_SEARCH_PAGES_QUERY_SELECTOR.toString()).last().attr(Configs.GIMY_SEARCH_RESULT_URL.toString()),
                gimyVideos);
    }

    @Override
    public List<GimyVideo> getListByPageUrlGimy(String url) {
        Document doc = RestTemplateProvider.htmlToDoc(url, HttpMethod.GET, false);
        List<GimyVideo> gimyVideos = new ArrayList<>();
        Elements elements = doc.select(Configs.GIMY_SEARCH_RESULT_QUERY_SELECTOR.toString());
        elements.forEach(element -> gimyVideos.add(new GimyVideo(element)));

        return gimyVideos;
    }

    @Override
    public GimyVideoDetail getGimyVideoDetail(String url) {
        Document doc = RestTemplateProvider.htmlToDoc(url, HttpMethod.GET, false);
        Elements channelElements = doc.select(Configs.GIMY_VIDEO_DETAIL_QYERY_SELECTOR.toString());

        List<Channel> channels = new ArrayList<>();
        channelElements.forEach(element -> {
            String channelName = element.select(Configs.GIMY_VIDEO_DETAIL_CHANNEL_NAME.toString()).text();
            List<VideoLink> videoLinks = new ArrayList<>();
            Elements videoLinkElements = element.select("ul.clearfix li a");
            videoLinkElements.forEach(videoLinkElement -> {
                String name = videoLinkElement.text();
                String link = Configs.GIMY_BASE + videoLinkElement.attr(Configs.GIMY_SEARCH_RESULT_URL.toString());
                videoLinks.add(new VideoLink(name, link));
            });
            channels.add(new Channel(channelName, videoLinks));
        });

        return new GimyVideoDetail(channels);
    }

    @Override
    public void watchGimyVideo(String url) {
        Document doc = RestTemplateProvider.htmlToDoc(url, HttpMethod.GET, false);
        System.out.println(doc);
    }

    public static void main(String[] args) {
        TubeImpl tube = new TubeImpl();

        String url = "https://gimy.app/eps/140031-1-1.html";
        tube.watchGimyVideo(url);

//        System.out.println(tube.getListByGimy("信號"));
    }
}
