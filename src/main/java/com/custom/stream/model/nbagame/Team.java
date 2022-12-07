package com.custom.stream.model.nbagame;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Team {
    private String name;
    private Player[] players;
}
