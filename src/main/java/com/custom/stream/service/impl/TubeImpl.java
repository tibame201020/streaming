package com.custom.stream.service.impl;

import com.custom.stream.provider.GimyParser;
import com.custom.stream.provider.RestTemplateProvider;
import com.custom.stream.provider.UrlParser;
import com.custom.stream.service.Tube;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TubeImpl implements Tube {
    private RestTemplate restTemplate = RestTemplateProvider.getRestTemplate();


    public static void main(String[] args) {
        String url = "https://gimy.app/vod/230059.html";

        GimyParser.getVideoByUrl(url);

    }
}
