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

public class RoundRobinTournamentSystem extends TournamentSystem {

    private final ITournamentTableService tournamentTableService;

    public RoundRobinTournamentSystem(
            ITournamentTableService tournamentTableService,
            IGameFixtureService gameFixtureService,
            IGroupService groupService,
            IParticipantService participantsService,
            Tournament tournament) {
        super(gameFixtureService, groupService, participantsService, tournament);
        this.tournamentTableService = tournamentTableService;
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
        return makeParticipantsNumberEven(tournament.getParticipantIds().size()) - 1;
    }

    @Override
    public void startNextTournamentStage() {

    }

    @Override
    public GameFixture finishGame(FinishGameUpdateEvent finishGameUpdateEvent) {
        var game = super.finishGame(finishGameUpdateEvent);
        tournamentTableService.update(buildTournamentTable(game.getGroupId()));
        return game;
    }
}
