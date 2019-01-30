package pl.wturnieju.tournament;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.wturnieju.model.CompetitionType;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TennisTournament extends Tournament {

    public TennisTournament() {
        setCompetitionType(CompetitionType.TENNIS);
    }
}
