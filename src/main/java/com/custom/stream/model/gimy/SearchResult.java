package com.custom.stream.model.gimy;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    private String firstHtml;
    private String pagesHtml;
    private int pages;
    private List<GimyVideo> gimyVideos;
}
