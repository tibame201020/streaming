package com.custom.stream.service;

import com.custom.stream.model.nbagame.Channel;
import com.custom.stream.model.nbagame.NbaGame;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Basket {

    List<NbaGame> getGames() throws Exception;

    List<Channel> getStreamChannel(String streamUrl) throws Exception;

    Map<String, Object> getStreamByClickChannel(String channelUrl, boolean isTryM3u8) throws Exception;

}
