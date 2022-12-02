package com.custom.stream.provider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParser {
    public static final Pattern ASSETS_JS_REGEX = Pattern.compile("\"assets\":.+?\"js\":\\s*\"([^\"]+)\"");
    public static final Pattern EMB_JS_REGEX = Pattern.compile("\"jsUrl\":\\s*\"([^\"]+)\"");

    public static final String EMBED_BASE = "https://www.youtube.com/embed/";

    public static final List<Pattern> YT_PLAYER_CONFIG_PATTERNS = Arrays.asList(
            Pattern.compile(";ytplayer\\.config = (\\{.*?\\})\\;ytplayer"),
            Pattern.compile(";ytplayer\\.config = (\\{.*?\\})\\;"),
            Pattern.compile("ytInitialPlayerResponse\\s*=\\s*(\\{.+?\\})\\s*\\;")
    );

    public static JsonObject getConfig(String url) {
        String html = RestTemplateProvider.getRestTemplate().getForObject(url, String.class);
        String ytPlayerConfig = null;

        for (Pattern pattern : YT_PLAYER_CONFIG_PATTERNS) {
            Matcher matcher = pattern.matcher(html);
            if (matcher.find()) {
                ytPlayerConfig = matcher.group(1);
                break;
            }
        }
        if (ytPlayerConfig == null) {
            throw new RuntimeException("Could not find player config on web page");
        }

        try {
            return new Gson().fromJson(ytPlayerConfig, JsonObject.class);
        } catch (Exception e) {
            throw new RuntimeException("Player config contains invalid json");
        }
    }

    public static String getJs(JsonObject config, String videoId) {
        String js = null;
        if (config.get("assets") != null && !config.get("assets").getAsString().isEmpty()) {
            js = new Gson().fromJson(config.get("assets").getAsString(), JsonObject.class).get("js").getAsString();
        } else {
            String html = RestTemplateProvider.getRestTemplate().getForObject(EMBED_BASE + videoId, String.class);
            Matcher matcher = ASSETS_JS_REGEX.matcher(html);
            if (matcher.find()) {
                js = matcher.group(1).replace("\\", "");
            } else {
                matcher = EMB_JS_REGEX.matcher(html);
                if (matcher.find()) {
                    js = matcher.group(1).replace("\\", "");
                }
            }
        }
        if (js == null) {
            throw new RuntimeException("get js url failed!");
        }
        return "https://youtube.com" + js;
    }
}
