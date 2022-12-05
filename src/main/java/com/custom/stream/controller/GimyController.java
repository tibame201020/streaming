package com.custom.stream.controller;

import com.custom.stream.model.gimy.GimyRankVideo;
import com.custom.stream.model.gimy.GimyVideo;
import com.custom.stream.model.gimy.GimyVideoDetail;
import com.custom.stream.model.gimy.SearchResult;
import com.custom.stream.service.Tube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/outer")
public class GimyController {

    @Autowired
    private Tube tube;

    @RequestMapping("/searchByKeyword")
    public SearchResult searchByKeyword(@RequestBody String keyword){
        return tube.getListByGimy(keyword);
    }

    @RequestMapping("/getListByPageUrlGimy")
    public List<GimyVideo> getListByPageUrlGimy(@RequestBody String url) {
        return tube.getListByPageUrlGimy(url);
    }

    @RequestMapping("/getGimyVideoDetail")
    public GimyVideoDetail getGimyVideoDetail(@RequestBody String url) {
        return tube.getGimyVideoDetail(url);
    }
    @RequestMapping("/watchGimyVideo")
    public String watchGimyVideo(@RequestBody String url) {
        return tube.watchGimyVideo(url);
    }
    @RequestMapping("/getGimyRankList")
    public List<GimyRankVideo> getGimyRankList() {
        return tube.getGimyRankList();
    }

}
