package com.custom.stream.provider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateProvider {
    private static final RestTemplate REST_TEMPLATE;
    private static HttpHeaders HEADERS = new HttpHeaders();
    private static HttpEntity HTTP = new HttpEntity<String>(HEADERS);

    static {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        REST_TEMPLATE = new RestTemplate(factory);

        HEADERS.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
        HEADERS.add("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.5,en;q=0.3");
    }

    public static String exchange(String url, HttpMethod httpMethod) {
        return REST_TEMPLATE.exchange(url, httpMethod, HTTP, String.class).getBody();
    }

    public static String toHtml(String url) {
        return REST_TEMPLATE.postForObject(url, HTTP, String.class);
    }

    public static Document htmlToDoc(String html, boolean isNeedClean) {
        return isNeedClean? Jsoup.parseBodyFragment(Jsoup.clean(html, Safelist.basic())) : Jsoup.parseBodyFragment(html);
    }

    public static Document htmlToDoc(String url, HttpMethod httpMethod, boolean isNeedClean) {
        return htmlToDoc(exchange(url, httpMethod), isNeedClean);
    }

}
