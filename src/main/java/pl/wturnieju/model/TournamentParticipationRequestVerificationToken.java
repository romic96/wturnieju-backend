package pl.wturnieju.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TournamentParticipationRequestVerificationToken extends VerificationToken {

    private String tournamentId;
}
