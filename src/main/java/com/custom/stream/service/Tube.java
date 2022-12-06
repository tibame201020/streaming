package com.custom.stream.service;

import com.custom.stream.model.gimy.GimyRankVideo;
import com.custom.stream.model.gimy.GimyVideo;
import com.custom.stream.model.gimy.GimyVideoDetail;
import com.custom.stream.model.gimy.SearchResult;

import java.util.List;
import java.util.Map;

public interface Tube {

    SearchResult getListByGimy(String keyword);

    List<GimyVideo> getListByPageUrlGimy(String url);

    GimyVideoDetail getGimyVideoDetail(String url);

    Map<String, String> watchGimyVideo(String url);

    List<GimyRankVideo> getGimyRankList();

    GimyVideo[] getPageListPageFromDb(long page);
}
