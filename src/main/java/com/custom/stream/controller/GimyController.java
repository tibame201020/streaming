package com.custom.stream.controller;

import com.custom.stream.model.gimy.GimyVideo;
import com.custom.stream.model.gimy.SearchResult;
import com.custom.stream.provider.GimyParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/outer")
public class GimyController {

    @RequestMapping("/searchByKeyWord")
    public SearchResult searchByKeyWord(@RequestBody String keyword) {
        return GimyParser.getSearchList(keyword);
    }

    @RequestMapping("/getVideosByUrl")
    public List<GimyVideo> getVideosByUrl(@RequestBody String url) {
        return GimyParser.getVideosByUrl(url);
    }
}
