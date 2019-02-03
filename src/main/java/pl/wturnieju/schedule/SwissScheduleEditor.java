package pl.wturnieju.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.ImmutablePair;

import pl.wturnieju.gamefixture.SwissGameFixture;
import pl.wturnieju.gamefixture.SwissGameFixtureFactory;
import pl.wturnieju.tournament.system.TournamentSystem;
import pl.wturnieju.tournament.system.state.SystemState;

public class SwissScheduleEditor extends ScheduleEditor<SwissGameFixture> {

    public SwissScheduleEditor(TournamentSystem<SystemState<SwissGameFixture>> tournamentSystem) {
        super(tournamentSystem, new SwissGameFixtureFactory());
    }

    @Override
    protected List<ImmutablePair<String, String>> getExcludedPairs() {
        List<ImmutablePair<String, String>> excludedPairs = new ArrayList<>();

        excludedPairs.addAll(getParticipantsPlayedEachOtherPairs());
        excludedPairs.addAll(getParticipantsWithByAsNullOpponent());

        return excludedPairs;
    }

    @Override
    protected BiFunction<String, String, Double> getWeightCalculationMethod() {
        return (a, b) -> 0.;
    }
}
