package pl.wturnieju.tournament.system.table;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Setter;
import pl.wturnieju.gamefixture.GameFixture;
import pl.wturnieju.tournament.GameResultType;
import pl.wturnieju.tournament.Participant;

public class TournamentTableGenerator {

    @Setter
    private Map<GameResultType, Double> resultToPointsMapping = new EnumMap<>(GameResultType.class);

    @Setter
    private List<? extends GameFixture> games = new ArrayList<>();

    @Setter
    private List<Participant> participants = new ArrayList<>();

    @Setter
    private Comparator<TournamentTableRow> rowComparator;

    private Map<String, TournamentTableRow> idToRowMapping = new HashMap<>();

    public TournamentTable generateTable() {
        generateRows();
        calculateRowsValues();

        return createTable();
    }

    private TournamentTable createTable() {
        var table = new TournamentTable();

        var rows = getSortedRows();
        table.setRows(rows);

        return table;
    }

    private List<TournamentTableRow> getSortedRows() {
        return idToRowMapping.values().stream()
                .sorted(rowComparator)
                .collect(Collectors.toList());
    }

    private void calculateRowsValues() {
        games.forEach(game -> {
            if (game.getBye()) {
                addByeGame(game);
            } else if (game.getWinner() == 0) {
                addDrawGame(game);
            } else {
                addWinGame(game);
            }
        });
    }

    private void addDrawGame(GameFixture game) {
        var homeParticipantRow = idToRowMapping.get(game.getHomeParticipant().getId());
        var awayParticipantRow = idToRowMapping.get(game.getAwayParticipant().getId());

        addToRowDraw(homeParticipantRow);
        addHomeParticipantsOpponentsSmallPoints(game, 0.5);
        addToRowDraw(awayParticipantRow);
        addAwayParticipantOpponentsSmallPoints(game, 0.5);

    }

    private void addByeGame(GameFixture game) {
        if (game.getHomeParticipantId() != null) {
            var row = idToRowMapping.get(game.getHomeParticipantId());
            addToRowWin(row);
        } else {
            var row = idToRowMapping.get(game.getAwayParticipantId());
            addToRowWin(row);
        }
    }

    private void addWinGame(GameFixture game) {
        var homeParticipantRow = idToRowMapping.get(game.getHomeParticipant().getId());
        var awayParticipantRow = idToRowMapping.get(game.getAwayParticipant().getId());

        if (game.getWinner() == 1) {
            addToRowWin(homeParticipantRow);
            addHomeParticipantsOpponentsSmallPoints(game, 1);
            addToRowLose(awayParticipantRow);
        } else {
            addToRowWin(awayParticipantRow);
            addAwayParticipantOpponentsSmallPoints(game, 1);
            addToRowLose(homeParticipantRow);
        }
    }

    private void addHomeParticipantsOpponentsSmallPoints(GameFixture game, double multiplier) {
        var row = idToRowMapping.get(game.getHomeParticipantId());
        var smallPointsToAdd = game.getAwaySmallPoints() * multiplier;
        row.setSmallPoints(row.getSmallPoints() + smallPointsToAdd);
    }

    private void addAwayParticipantOpponentsSmallPoints(GameFixture game, double multiplier) {
        var row = idToRowMapping.get(game.getAwayParticipantId());
        var smallPointsToAdd = game.getAwaySmallPoints() * multiplier;
        row.setSmallPoints(row.getSmallPoints() + smallPointsToAdd);
    }

    private void addToRowWin(TournamentTableRow row) {
        var manager = new TournamentTableRowManager(row);
        manager.addPoints(getPoints(GameResultType.WIN));
        manager.increaseWins();
        manager.increaseTotalGames();
    }

    private void addToRowDraw(TournamentTableRow row) {
        var manager = new TournamentTableRowManager(row);
        manager.addPoints(getPoints(GameResultType.DRAW));
        manager.increaseDraws();
        manager.increaseTotalGames();
    }

    private void addToRowLose(TournamentTableRow row) {
        var manager = new TournamentTableRowManager(row);
        manager.addPoints(getPoints(GameResultType.LOSE));
        manager.increaseLoses();
        manager.increaseTotalGames();
    }

    private Double getPoints(GameResultType resultType) {
        return resultToPointsMapping.get(resultType);
    }

    private void generateRows() {
        participants.forEach(participant -> {
            var row = new TournamentTableRow(participant.getId(), participant.getName());
            idToRowMapping.put(participant.getId(), row);
        });
    }
}
