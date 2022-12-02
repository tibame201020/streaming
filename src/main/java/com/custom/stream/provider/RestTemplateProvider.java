package com.custom.stream.provider;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateProvider {
    private static final RestTemplate REST_TEMPLATE;
    private static HttpHeaders HEADERS = new HttpHeaders();

    static {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        REST_TEMPLATE = new RestTemplate(factory);

        HEADERS.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
        HEADERS.add("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        HEADERS.add("cookie", "_ga=GA1.1.256354306.1669961634; PHPSESSID=s2h1ae3ef27tsiq2so8ikv6v4g; __atuvc=1|48; __atuvs=6389b2f624ee3358000; _ga_PQY0S97M8C=GS1.1.1669968630.2.0.1669968630.0.0.0");
        HEADERS.add("authority", "gimy.app");
        HEADERS.add("referer", "https://gimy.app");
        HEADERS.add("method", "GET");
    }

    public static RestTemplate getRestTemplate(){
        return REST_TEMPLATE;
    }

    public static HttpEntity getEntity() {
        return new HttpEntity<String>(HEADERS);
    }

}
