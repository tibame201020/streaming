package com.custom.stream.provider;

import com.custom.stream.model.gimy.GimyVideo;
import com.custom.stream.model.gimy.SearchResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

public class GimyParser {

    public static final String BASE_URL = "https://gimy.app";
    public static final String BASE_SEARCH_URL = "https://gimy.app/search/-------------.html?wd=";
    public static final String SPLIT_BEGIN = "video-pic loading\" ";
    public static final String SPLIT_END = "details-info ";

    public static final String VIDEO_STR_END =" style=\"padding-top:";

    public static SearchResult getSearchList(String keyword) {
        String resultHtml = RestTemplateProvider.getRestTemplate().postForObject(BASE_SEARCH_URL + keyword, RestTemplateProvider.getEntity(), String.class);
        List<GimyVideo> gimyVideos = new ArrayList<>(wrapperResultHtml(resultHtml));
        String pagesHtml = getPagesHtml(resultHtml);
        int pages = getPages(pagesHtml);
        System.out.println(new SearchResult(BASE_SEARCH_URL + keyword, pagesHtml, pages, gimyVideos));
        return new SearchResult(BASE_SEARCH_URL + keyword, pagesHtml, pages, gimyVideos);
    }

    public static List<GimyVideo> getVideosByUrl(String url) {
        String resultHtml = RestTemplateProvider.getRestTemplate().postForObject(url, RestTemplateProvider.getEntity(), String.class);
        return wrapperResultHtml(resultHtml);
    }

    public static void getVideoByUrl(String url) {
        String resultHtml = RestTemplateProvider.getRestTemplate().exchange(url, HttpMethod.GET,RestTemplateProvider.getEntity(),String.class).getBody();
        String subBy = "playlist-mobile playlist layout-box clearfix";
        resultHtml = resultHtml.split(subBy)[1];
        wrapperVideoNumUrls(resultHtml);
    }

    private static void wrapperVideoNumUrls(String html) {
        for (String s: html.split("<li><a href=\"")) {
            if (s.contains("集")) {
                System.out.println(s.substring(0, s.indexOf("</a></li>")));
            }
        }
    }

    private static List<GimyVideo> wrapperResultHtml(String resultHtml) {
        List<GimyVideo> gimyVideos = new ArrayList<>();
        for (String s : resultHtml.split(SPLIT_BEGIN)) {
            if (s.contains(SPLIT_END)) {
                String videoStr = s.substring(0, s.indexOf(SPLIT_END));
                videoStr = videoStr.substring(0, videoStr.indexOf(VIDEO_STR_END));
                GimyVideo gimyVideo = new GimyVideo(videoStr);
                gimyVideos.add(gimyVideo);
            }
        }
        return gimyVideos;
    }

    private static String getPagesHtml(String resultHtml) {
        return resultHtml.substring(resultHtml.indexOf("下一頁</a></li>") + 36, resultHtml.indexOf("尾頁") - 23);
    }
    private static int getPages(String pagesHtml) {
        return Integer.parseInt(pagesHtml.substring(pagesHtml.indexOf("----------") + 10, pagesHtml.indexOf("---.html")));
    }


}
