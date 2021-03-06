package pl.wturnieju.tournament.system;

import java.util.Collections;
import java.util.Map;

import pl.wturnieju.gameeditor.finish.FinishGameUpdateEvent;
import pl.wturnieju.gamefixture.GameFixture;
import pl.wturnieju.service.IGameFixtureService;
import pl.wturnieju.service.IGroupService;
import pl.wturnieju.service.IParticipantService;
import pl.wturnieju.service.ITournamentTableService;
import pl.wturnieju.tournament.LegType;
import pl.wturnieju.tournament.Tournament;

public class SwissTournamentSystem extends TournamentSystem {

    protected final ITournamentTableService tournamentTableService;

    public SwissTournamentSystem(ITournamentTableService tournamentTableService,
            IGameFixtureService gameFixtureService,
            IGroupService groupService,
            IParticipantService participantsService, Tournament tournament) {
        super(gameFixtureService, groupService, participantsService, tournament);
        this.tournamentTableService = tournamentTableService;
    }

    @Override
    public void startNextTournamentStage() {

    }

    @Override
    public void startTournament() {
        super.startTournament();
        prepareSingleGroupTournament();
    }

    @Override
    protected Map<Integer, LegType> createRoundToLegMapping() {
        return Collections.emptyMap();
    }

    @Override
    protected int calculatePlannedRounds() {
        var participantsNumber = makeParticipantsNumberEven(tournament.getParticipantIds().size());
        return (int) Math.ceil(Math.log(participantsNumber) / Math.log(2));
    }

    @Override
    public GameFixture finishGame(FinishGameUpdateEvent finishGameUpdateEvent) {
        var game = super.finishGame(finishGameUpdateEvent);
        tournamentTableService.update(buildTournamentTable(game.getGroupId()));
        return game;
    }
}
