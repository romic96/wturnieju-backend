package pl.wturnieju.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.wturnieju.controller.dto.tournament.ParticipantDto;
import pl.wturnieju.controller.dto.tournament.ParticipantDtoMapper;
import pl.wturnieju.controller.dto.tournament.TournamentDto;
import pl.wturnieju.controller.dto.tournament.TournamentDtoMapper;
import pl.wturnieju.controller.dto.tournament.UserTournamentsDto;
import pl.wturnieju.controller.dto.tournament.UserTournamentsDtoMapper;
import pl.wturnieju.controller.dto.tournament.gamefixture.GameFixtureDtoMapper;
import pl.wturnieju.controller.dto.tournament.schedule.ScheduleDto;
import pl.wturnieju.controller.dto.tournament.schedule.ScheduleDtoMapper;
import pl.wturnieju.controller.dto.tournament.schedule.ScheduleElementDtoMapper;
import pl.wturnieju.controller.dto.tournament.table.TournamentTableDto;
import pl.wturnieju.controller.dto.tournament.table.TournamentTableDtoMapper;
import pl.wturnieju.gamefixture.GameFixture;
import pl.wturnieju.tournament.Participant;
import pl.wturnieju.tournament.Tournament;
import pl.wturnieju.tournament.system.table.TournamentTable;

@Component
@RequiredArgsConstructor
@Getter
public class DtoMappers {

    private final ScheduleDtoMapper scheduleDtoMapper;

    private final ScheduleElementDtoMapper scheduleElementDtoMapper;

    private final TournamentTableDtoMapper tournamentTableDtoMapper;

    private final ParticipantDtoMapper participantDtoMapper;

    private final TournamentDtoMapper tournamentDtoMapper;

    private final UserTournamentsDtoMapper userTournamentsDtoMapper;

    private final GameFixtureDtoMapper gameFixtureDtoMapper;


    public UserTournamentsDto createUserTournamentDto(String userId, List<Tournament> tournaments) {
        return userTournamentsDtoMapper.tournamentsToUserTournamentDto(userId, tournaments);
    }

    public ParticipantDto createTournamentParticipantDto(Participant participant) {
        return participantDtoMapper.participantToParticipantDto(participant);
    }

    public TournamentDto createTournamentDto(Tournament tournament) {
        return tournamentDtoMapper.tournamentToTournamentDto(tournament);
    }

    public TournamentTableDto createTournamentTableDto(TournamentTable table) {
        return tournamentTableDtoMapper.tournamentTableToTournamentTableDto(table);
    }

    public ScheduleDto createScheduleDto(String tournamentId, Integer round, List<GameFixture> schedule) {
        return getScheduleDtoMapper().toScheduleDto(tournamentId, round, schedule);
    }

}
