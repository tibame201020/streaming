package com.custom.stream.model.gimy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Channel implements Serializable {
    private String name;
    private List<VideoLink> videoLinks;
}
