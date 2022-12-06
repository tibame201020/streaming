package com.custom.stream.service;

import com.custom.stream.model.gimy.*;

import java.util.List;
import java.util.Map;

public interface Tube {

    SearchResult getListByGimy(String keyword);

    List<GimyVideo> getListByPageUrlGimy(String url);

    GimyVideoDetail getGimyVideoDetail(String url);

    Map<String, String> watchGimyVideo(String url);

    List<GimyRankVideo> getGimyRankList();

    List<GimyVideo> getPageListPageFromDb(GimyPageReq page);
}
