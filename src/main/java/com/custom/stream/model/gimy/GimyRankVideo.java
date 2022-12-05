package com.custom.stream.model.gimy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class GimyRankVideo {
    private String category;
    private List<GimyVideo> gimyVideos;
}
