package com.custom.stream.service;

import com.custom.stream.model.gimy.GimyRankVideo;
import com.custom.stream.model.gimy.GimyVideo;
import com.custom.stream.model.gimy.GimyVideoDetail;
import com.custom.stream.model.gimy.SearchResult;

import java.util.List;

public interface Tube {

    SearchResult getListByGimy(String keyword);

    List<GimyVideo> getListByPageUrlGimy(String url);

    GimyVideoDetail getGimyVideoDetail(String url);

    String watchGimyVideo(String url);

    List<GimyRankVideo> getGimyRankList();
}
