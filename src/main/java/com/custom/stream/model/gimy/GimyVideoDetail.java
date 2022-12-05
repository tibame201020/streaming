package com.custom.stream.model.gimy;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GimyVideoDetail implements Serializable {
    private List<Channel> channels;
}