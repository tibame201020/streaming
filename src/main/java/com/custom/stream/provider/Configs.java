package com.custom.stream.provider;

public enum Configs {
    GIMY_SEARCH_BASE("https://gimy.app/search/-------------.html?wd="),
    GIMY_BASE("https://gimy.app"),
    GIMY_SEARCH_RESULT_QUERY_SELECTOR(".video-pic.loading"),
    GIMY_SEARCH_RESULT_TITLE("title"),
    GIMY_SEARCH_RESULT_URL("href"),
    GIMY_SEARCH_RESULT_IMG("data-original"),

    GIMY_SEARCH_PAGES_QUERY_SELECTOR(".pagegbk"),
    GIMY_VIDEO_DETAIL_QYERY_SELECTOR(".playlist-mobile"),
    GIMY_VIDEO_DETAIL_CHANNEL_NAME(".gico"),
    GIMY_VIDEO_DETAIL_CHANNEL_SELECTOR("ul.clearfix li a"),

    GIMY_RANK_URL("https://gimy.app/label/rank.html"),

    GIMY_VIDEO_DETAIL_PLAYER_ID("#zanpiancms_player"),
    GIMY_VIDEO_DETAIL_M3U8_REGEX("\"url\":\"(.*)\",\"url_next"),
    GIMY_SEARCH_PAGE(GIMY_BASE + "/search/%s----------%s---\\.html");

    private final String str;

    Configs(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
