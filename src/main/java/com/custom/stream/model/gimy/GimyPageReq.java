package com.custom.stream.model.gimy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class GimyPageReq {
    private String keyword;
    private String page;
}
