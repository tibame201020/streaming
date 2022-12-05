package com.custom.stream.model.gimy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class VideoLink implements Serializable {
    private String name;
    private String link;
}
