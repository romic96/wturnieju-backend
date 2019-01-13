package pl.wturnieju.controller.dto.gamefixture;

import lombok.Getter;
import lombok.Setter;
import pl.wturnieju.controller.dto.ScoreDto;
import pl.wturnieju.controller.dto.TeamDto;

@Getter
@Setter
public class GameFixtureDto {

    private String gameId;

    private TeamDto homeTeam;

    private ScoreDto homeScore;

    private TeamDto awayTeam;

    private ScoreDto awayScore;
}