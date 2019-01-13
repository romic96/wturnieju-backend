package pl.wturnieju.service;

import java.util.List;

import pl.wturnieju.gamefixture.GameFixture;

public interface ITournamentScheduleService {

    List<GameFixture> generateSchedule(String tournamentId);

    GameFixture updateGameFixture(String tournamentId, GameFixture gameFixture);

    List<GameFixture> saveSchedule(String tournamentId, List<GameFixture> games);

    List<GameFixture> getGeneratedSchedule(String tournamentId, List<String> gameIds);
}