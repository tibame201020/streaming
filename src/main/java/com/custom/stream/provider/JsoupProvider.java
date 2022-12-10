package com.custom.stream.provider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

public class JsoupProvider {

    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36";
    public static String withUserAgent(String url) {
        try {
            return Jsoup.connect(url).ignoreContentType(true).userAgent(USER_AGENT).post().toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static Document urlToDoc(String url, boolean isNeedClean) {
        return isNeedClean ? Jsoup.parseBodyFragment(Jsoup.clean(withUserAgent(url), Safelist.basic())) : Jsoup.parseBodyFragment(withUserAgent(url));
    }
}
