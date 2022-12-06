package com.custom.stream.service.impl;

import com.custom.stream.model.gimy.*;
import com.custom.stream.provider.RestTemplateProvider;
import com.custom.stream.repo.TempPagesDataRepo;
import com.custom.stream.service.Tube;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.custom.stream.provider.Configs.*;

@Service
public class TubeImpl implements Tube {
    @Autowired
    private TempPagesDataRepo tempPagesDataRepo;


    @Override
    public SearchResult getListByGimy(String keyword) {
        deleteTempPageData();

        String url = GIMY_SEARCH_BASE + keyword;
        Document doc = RestTemplateProvider.htmlToDoc(url, HttpMethod.GET, false);

        List<GimyVideo> gimyVideos = new ArrayList<>();
        Elements elements = doc.select(GIMY_SEARCH_RESULT_QUERY_SELECTOR.toString());
        elements.forEach(element -> gimyVideos.add(new GimyVideo(element)));

        String pagesHtml;
        int totalPages;

        try {
            pagesHtml = GIMY_BASE +
                    doc.select(GIMY_SEARCH_PAGES_QUERY_SELECTOR.toString()).last().attr(GIMY_SEARCH_RESULT_URL.toString());
        } catch (Exception e) {
            pagesHtml = "";
        }

        Pattern pattern = Pattern.compile(GIMY_BASE + "/search/(.*)----------(.*)---\\.html");
        Matcher matcher = pattern.matcher(pagesHtml);
        if (!matcher.find()) {
            totalPages = 1;
        } else {
            totalPages = Integer.parseInt(matcher.group(2));
        }
        String finalPagesHtml = pagesHtml;
        new Thread(() -> getPagesData(finalPagesHtml, totalPages, gimyVideos.stream().toArray(GimyVideo[]::new))).start();

        return new SearchResult(
                url,
                totalPages,
                gimyVideos);
    }

    private void getPagesData(String pagesUrl, int totalPages, GimyVideo[] firstPageData) {
        tempPagesDataRepo.save(new TempPagesData(1, firstPageData));
        for (int i = 2; i <= totalPages ; i++) {
            String url = pagesUrl.replace(String.valueOf(totalPages), String.valueOf(i));
            List<GimyVideo> gimyVideos = getListByPageUrlGimy(url);
            tempPagesDataRepo.save(new TempPagesData((long) i, gimyVideos.stream().toArray(GimyVideo[]::new)));
        }
    }

    private void deleteTempPageData() {
        tempPagesDataRepo.deleteAll();
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
            if (videoLinks.size() > 0) {
                channels.add(new Channel(channelName, videoLinks));
            }
        });

        return new GimyVideoDetail(channels);
    }

    @Override
    public Map<String, String> watchGimyVideo(String url) {
        Map<String, String> resultMap = new LinkedHashMap<>();

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
        resultMap.put("url", m3u8.toString());
        return resultMap;
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

    @Override
    public GimyVideo[] getPageListPageFromDb(long page) {

        for (TempPagesData pagesData:
             tempPagesDataRepo.findAll()) {
            System.out.println(pagesData);
        }

        TempPagesData tempPagesData = tempPagesDataRepo.findById(page).orElse(new TempPagesData());

        return tempPagesData.getGimyVideos();
    }

    public static void main(String[] args) {
        TubeImpl tube = new TubeImpl();
        tube.getListByGimy("信");
    }
}
