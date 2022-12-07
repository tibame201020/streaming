package com.custom.stream.model.nbagame;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.nodes.Element;

@ToString
@Setter
@Getter
public class NbaGame {
    private String gameTime;
    private String homeTeamName;
    private String homeTeamLogo;
    private String awayTeamName;
    private String awayTeamLogo;

    private String streamUrl;

    public NbaGame(Element nbaGame) {
        this.gameTime = nbaGame.select(".status").text();
        this.awayTeamName = nbaGame.select(".team-name").first().text();
        this.awayTeamLogo =
            nbaGame.select(".team-logo").first().select("img").attr("src");
        this.homeTeamName = nbaGame.select(".team-name").last().text();
        this.homeTeamLogo =
            nbaGame.select(".team-logo").last().select("img").attr("src");
        this.streamUrl = nbaGame.firstElementChild().attr("href");
    }
}
