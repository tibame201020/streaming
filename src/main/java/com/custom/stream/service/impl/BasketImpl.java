package com.custom.stream.service.impl;

import com.custom.stream.model.nbagame.Channel;
import com.custom.stream.model.nbagame.NbaGame;
import com.custom.stream.service.Basket;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.custom.stream.provider.Configs.NBA_STREAM_CHANNEL_BASE_URL;
import static com.custom.stream.provider.Configs.NBA_STREAM_INDEX_URL;
import static com.custom.stream.provider.JsoupProvider.urlToDoc;

@Service
public class BasketImpl implements Basket {

    @Override
    public List<NbaGame> getGames() throws Exception {
        Document document = urlToDoc(NBA_STREAM_INDEX_URL.toString(), false);
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
        Document document = urlToDoc(streamUrl, false);

        String html = document.toString();
        String channelIdTag = "streamsMatchId";
        String channelIdEndTag = "var streamsSport";

        int startIdx = html.indexOf(channelIdTag) + channelIdTag.length() + 3;
        int endIdx = html.indexOf(channelIdEndTag);
        String channelId = html.substring(startIdx, endIdx);
        channelId = channelId.trim().substring(0, channelId.trim().length() -1);

        String channelUrl = String.format(NBA_STREAM_CHANNEL_BASE_URL.toString(), channelId);
        document = urlToDoc(channelUrl, false);

        List<Channel> channels = new ArrayList<>();

        Elements channelElements = document.select("tbody tr");
        for (Element channelElement:
             channelElements) {
            channels.add(new Channel(channelElement));
            if (channels.size() > 10) {
                break;
            }
        }
        return channels;
    }

    @Override
    public Map<String, Object> getStreamByClickChannel(String channelUrl, boolean isTryM3u8) throws Exception {
        Document document = urlToDoc(channelUrl, false);
        String streamUrl = document.select(".navbar-collapse").select("a").attr("href");

        Map<String, Object> rtnMap = new LinkedHashMap<>();
        String url = streamUrl;
        boolean isM3u8 = false;

        if (isTryM3u8) {
            isM3u8 = true;
            if (streamUrl.contains("weakstreams")) {
                url = handleWeakstreams(streamUrl);
            }
        }

        rtnMap.put("url", url);
        rtnMap.put("isM3u8", isM3u8);

        return rtnMap;
    }

    private String handleWeakstreams(String url) throws Exception {
        return Jsoup.parse(urlToDoc(url, false).select("#gamecard").select("textarea").text()).select("iframe").attr("src");
    }



}
