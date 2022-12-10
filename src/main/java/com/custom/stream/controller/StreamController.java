package com.custom.stream.controller;

import com.custom.stream.model.nbagame.Channel;
import com.custom.stream.model.nbagame.NbaGame;
import com.custom.stream.service.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/outer")
public class StreamController {
    @Autowired
    private Basket basket;

    @RequestMapping("/getGames")
    public List<NbaGame> getGames() throws Exception {
        return basket.getGames();
    }
    @RequestMapping("/getStreamChannel")
    public List<Channel> getStreamChannel(@RequestBody String streamUrl) throws Exception {
        return basket.getStreamChannel(streamUrl);
    }
    @RequestMapping("/getStreamByClickChannel")
    public Map<String, Object> getStreamByClickChannel(@RequestBody String channelUrl) throws Exception {
        return basket.getStreamByClickChannel(channelUrl, true);
    }

    @RequestMapping("/getStreamToExternal")
    public Map<String, Object> getStreamToExternal(@RequestBody String channelUrl) throws Exception {
        return basket.getStreamByClickChannel(channelUrl, false);
    }
}
