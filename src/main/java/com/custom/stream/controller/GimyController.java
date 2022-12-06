package com.custom.stream.controller;

import com.custom.stream.model.gimy.*;
import com.custom.stream.service.Tube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public List<GimyVideo> getListByPageUrlGimy(@RequestBody GimyPageReq gimyPageReq) {
        return tube.getPageListPageFromDb(gimyPageReq);
    }

    @RequestMapping("/getGimyVideoDetail")
    public GimyVideoDetail getGimyVideoDetail(@RequestBody String url) {
        return tube.getGimyVideoDetail(url);
    }
    @RequestMapping("/watchGimyVideo")
    public Map<String, String> watchGimyVideo(@RequestBody String url) {
        return tube.watchGimyVideo(url);
    }
    @RequestMapping("/getGimyRankList")
    public List<GimyRankVideo> getGimyRankList() {
        return tube.getGimyRankList();
    }

    @RequestMapping("/getGimyHistory")
    public List<GimyHistory> getGimyHistory(){
        return tube.getGimyHistory();
    }

    @RequestMapping("/addGimyHistory")
    public void addGimyHistory(@RequestBody GimyHistory gimyHistory){
        tube.addGimyHistory(gimyHistory);
    }

}
