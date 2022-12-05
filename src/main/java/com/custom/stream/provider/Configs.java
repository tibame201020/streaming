package com.custom.stream.provider;

public enum Configs {
    GIMY_SEARCH_BASE("https://gimy.app/search/-------------.html?wd="),
    GIMY_BASE("https://gimy.app"),
    GIMY_SEARCH_RESULT_QUERY_SELECTOR("#content .video-pic"),
    GIMY_SEARCH_RESULT_TITLE("title"),
    GIMY_SEARCH_RESULT_URL("href"),
    GIMY_SEARCH_RESULT_IMG("data-original"),

    GIMY_SEARCH_PAGES_QUERY_SELECTOR(".pagegbk"),
    GIMY_VIDEO_DETAIL_QYERY_SELECTOR(".playlist-mobile"),
    GIMY_VIDEO_DETAIL_CHANNEL_NAME(".gico");
    Configs(String str) {
        this.str = str;
    }
    private final String str;

    @Override
    public String toString() {
        return str;
    }
}