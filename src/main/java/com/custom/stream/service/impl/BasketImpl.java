package com.custom.stream.service.impl;

import com.custom.stream.model.nbagame.Channel;
import com.custom.stream.model.nbagame.NbaGame;
import com.custom.stream.service.Basket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.custom.stream.provider.Configs.NBA_STREAM_INDEX_URL;

@Service
public class BasketImpl implements Basket {

    public static void main(String[] args) throws Exception {

        BasketImpl basket = new BasketImpl();
        for (NbaGame nbaGame:
        basket.getGames()) {
            for (Channel channel:
            basket.getStreamChannel(nbaGame.getStreamUrl())) {
                for (String key:
                basket.getStreamByClickChannel(channel.getUrl()).keySet()) {
                    System.out.println(key + "=" + basket.getStreamByClickChannel(channel.getUrl()).get(key));
                }
            }
        }

    }

    @Override
    public List<NbaGame> getGames() throws Exception {
        Document document = Jsoup.connect(NBA_STREAM_INDEX_URL.toString()).get();
        Element nbaGamesCollection = document.select("#competitions").select("div.competition").first();
        Elements nbaGameElements = nbaGamesCollection.select(".matches").select("div.col-md-6");

        List<NbaGame> nbaGames = new ArrayList<>();

        for (Element nbaGameElement:
                nbaGameElements) {
            nbaGames.add(new NbaGame(nbaGameElement));
        }

        return nbaGames;
    }

    @Override
    public List<Channel> getStreamChannel(String streamUrl) throws Exception {
        Document document = Jsoup.connect(streamUrl).get();

        System.out.println(document);

        String html = document.toString();
        String channelIdTag = "streamsMatchId";
        String channelIdEndTag = "var streamsSport";

        int startIdx = html.indexOf(channelIdTag) + channelIdTag.length() + 3;
        int endIdx = html.indexOf(channelIdEndTag);
        String channelId = html.substring(startIdx, endIdx);
        channelId = channelId.trim().substring(0, channelId.trim().length() -1);

        String channelBaseUrl = "https://sportscentral.io/streams-table/%s/basketball?new-ui=1&origin=www1.nbabite.com";
        String channelUrl = String.format(channelBaseUrl, channelId);
        document = Jsoup.connect(channelUrl).get();

        List<Channel> channels = new ArrayList<>();

        Elements channelElements = document.select("tbody tr");
        for (Element channelElement:
             channelElements) {
            channels.add(new Channel(channelElement));
        }
        return channels;
    }

    @Override
    public Map<String, Object> getStreamByClickChannel(String channelUrl) throws Exception {
        Document document = Jsoup.connect(channelUrl).get();
        String streamUrl = document.select(".navbar-collapse").select("a").attr("href");

        Map<String, Object> rtnMap = new LinkedHashMap<>();
        String url = streamUrl;
        boolean isM3u8 = false;

        if (streamUrl.contains("1stream")) {
            url = handle1Stream(streamUrl);
            isM3u8 = true;
        } else if (streamUrl.contains("weakstreams")) {
            url = handleWeakstreams(streamUrl);
            isM3u8 = true;
        } else if (streamUrl.contains("topstreams")) {
            url = handleToptreams(streamUrl);
            isM3u8 = true;
        }

        rtnMap.put("url", url);
        rtnMap.put("isM3u8", isM3u8);

        return rtnMap;
    }

    @Override
    public String readHtmlStr(String url) throws Exception {
        return Jsoup.connect(url).get().html();
    }


    private String handle1Stream(String streamUrl) throws Exception {
        String regex = "(.*)1stream(.*)live-stream/(.*)?sport(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(streamUrl);
        String channelId = "";

        if (matcher.find()) {
            channelId = matcher.group(3).replace("?", "");
        }

        String getStreamUrl = "http://1stream.link/getspurcename";
        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
        Connection.Response res = Jsoup.connect(getStreamUrl).userAgent(USER_AGENT).ignoreContentType(true)
                .data("eventId", channelId)
                .data("sport", "basketball")
                .method(Connection.Method.POST).execute();

        return new Gson().fromJson(res.body(), JsonObject.class).get("source").toString();
    }

    private String handleWeakstreams(String streamUrl) throws Exception {
        Document document = Jsoup.connect(streamUrl).get();

        String html = document.toString();
        String startIdTag = "vidgstream = \"";
        int startIdx = html.indexOf(startIdTag);
        String idgstream = html.substring(startIdx);
        String endIdTag = "\n";
        int endIdx = idgstream.indexOf(endIdTag);
        idgstream = idgstream.substring(startIdTag.length(), endIdx - 3);

        String getStreamUrl = "https://weakstreams.com/gethls";
        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
        Connection.Response res = Jsoup.connect(getStreamUrl).userAgent(USER_AGENT).ignoreContentType(true)
                .data("idgstream", idgstream)
                .method(Connection.Method.GET).execute();

        return new Gson().fromJson(res.body(), JsonObject.class).get("rawUrl").toString();
    }

    private String handleToptreams(String streamUrl) throws Exception {
        Document document = Jsoup.connect(streamUrl).get();
        String html = document.toString();
        String startIdTag = "globalurl= '";
        int startIdx = html.indexOf(startIdTag);
        String globalurl = html.substring(startIdx);
        String endIdTag = "index.m3u8";
        int endIdx = globalurl.indexOf(endIdTag);
        globalurl = globalurl.substring(startIdTag.length(), endIdx) + endIdTag;

        return globalurl;
    }


}
