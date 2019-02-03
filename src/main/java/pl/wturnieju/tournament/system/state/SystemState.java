package pl.wturnieju.tournament.system.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import pl.wturnieju.gamefixture.GameFixture;
import pl.wturnieju.gamefixture.GameStatus;
import pl.wturnieju.model.Persistent;
import pl.wturnieju.model.Timestamp;


@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemState extends Persistent {

    private Timestamp lastUpdate;

    private List<String> participantsWithBye = new ArrayList<>();

    private String tournamentId;

    private Map<String, List<String>> participantsPlayedEachOther = new HashMap<>();

    private List<Group> knockoutGroups = new ArrayList<>();

    private List<Group> groups = new ArrayList<>();

    private List<GameFixture> gameFixtures = new ArrayList<>();

    private List<GameFixture> generatedGameFixtures = new ArrayList<>();

    @Transient
    public List<GameFixture> getEndedGames() {
        return gameFixtures.stream()
                .filter(game -> game.getGameStatus() == GameStatus.ENDED)
                .collect(Collectors.toList());
    }
}
