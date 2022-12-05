package com.custom.stream.service.impl;

import com.custom.stream.model.gimy.*;
import com.custom.stream.provider.RestTemplateProvider;
import com.custom.stream.service.Tube;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.custom.stream.provider.Configs.*;

@Service
public class TubeImpl implements Tube {


    @Override
    public SearchResult getListByGimy(String keyword) {
        String url = GIMY_SEARCH_BASE + keyword;
        Document doc = RestTemplateProvider.htmlToDoc(url, HttpMethod.GET, false);
        List<GimyVideo> gimyVideos = new ArrayList<>();
        Elements elements = doc.select(GIMY_SEARCH_RESULT_QUERY_SELECTOR.toString());
        elements.forEach(element -> gimyVideos.add(new GimyVideo(element)));
        return new SearchResult(
                url,
                GIMY_BASE +
                        doc.select(GIMY_SEARCH_PAGES_QUERY_SELECTOR.toString()).last().attr(GIMY_SEARCH_RESULT_URL.toString()),
                gimyVideos);
    }

    @Override
    public List<GimyVideo> getListByPageUrlGimy(String url) {
        Document doc = RestTemplateProvider.htmlToDoc(url, HttpMethod.GET, false);
        List<GimyVideo> gimyVideos = new ArrayList<>();
        Elements elements = doc.select(GIMY_SEARCH_RESULT_QUERY_SELECTOR.toString());
        elements.forEach(element -> gimyVideos.add(new GimyVideo(element)));

        return gimyVideos;
    }

    @Override
    public GimyVideoDetail getGimyVideoDetail(String url) {
        Document doc = RestTemplateProvider.htmlToDoc(url, HttpMethod.GET, false);
        Elements channelElements = doc.select(GIMY_VIDEO_DETAIL_QYERY_SELECTOR.toString());

        List<Channel> channels = new ArrayList<>();
        channelElements.forEach(element -> {
            String channelName = element.select(GIMY_VIDEO_DETAIL_CHANNEL_NAME.toString()).text();
            List<VideoLink> videoLinks = new ArrayList<>();
            Elements videoLinkElements = element.select(GIMY_VIDEO_DETAIL_CHANNEL_SELECTOR.toString());
            videoLinkElements.forEach(videoLinkElement -> {
                String name = videoLinkElement.text();
                String link = GIMY_BASE + videoLinkElement.attr(GIMY_SEARCH_RESULT_URL.toString());
                videoLinks.add(new VideoLink(name, link));
            });
            channels.add(new Channel(channelName, videoLinks));
        });

        return new GimyVideoDetail(channels);
    }

    @Override
    public String watchGimyVideo(String url) {
        Document doc = RestTemplateProvider.htmlToDoc(url, HttpMethod.GET, false);
        Elements element = doc.select(GIMY_VIDEO_DETAIL_PLAYER_ID.toString());
        Pattern pattern = Pattern.compile(GIMY_VIDEO_DETAIL_M3U8_REGEX.toString(), Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(element.toString());
        StringBuilder m3u8 = new StringBuilder();
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                m3u8.append(matcher.group(i));
            }
        }
        return m3u8.toString();
    }

    @Override
    public List<GimyRankVideo> getGimyRankList() {
        List<GimyRankVideo> gimyRankVideos = new ArrayList<>();

        Document doc = RestTemplateProvider.htmlToDoc(GIMY_RANK_URL.toString(), HttpMethod.GET, false);
        Elements elements = doc.select(".box-video-text-list").prev();
        elements.forEach(element -> {
            String category = element.select("h3.m-0").text().substring(1);
            List<GimyVideo> gimyVideos = new ArrayList<>();
            element.nextElementSibling().select("li a").forEach(
                    video -> gimyVideos.add(new GimyVideo(video))
            );
            gimyRankVideos.add(new GimyRankVideo(category, gimyVideos));
        });

        return gimyRankVideos;
    }
}
