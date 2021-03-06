package pl.wturnieju.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.wturnieju.model.AccessOption;
import pl.wturnieju.repository.TournamentRepository;
import pl.wturnieju.service.ITournamentService;
import pl.wturnieju.tournament.Tournament;
import pl.wturnieju.tournament.TournamentStatus;
import pl.wturnieju.tournament.system.TournamentSystemFactory;

@Service
@RequiredArgsConstructor
public class TournamentService implements ITournamentService {

    private final TournamentRepository tournamentRepository;

    private final ApplicationContext context;

    @Override
    public List<Tournament> getTournamentsOwnedByUserId(String userId) {
        return tournamentRepository.getAllByOwner_Id(userId);
    }

    @Override
    public void startTournament(String tournamentId) {
        var tournament = findTournament(tournamentId).orElseThrow(ResourceNotFoundException::new);
        validateTournamentParticipants(tournament);
        var system = TournamentSystemFactory.create(context, tournament);
        system.startTournament();
        tournament.setStatus(TournamentStatus.IN_PROGRESS);
        tournamentRepository.save(tournament);
    }

    @Override
    public void finishTournament(String tournamentId) {
        var tournament = findTournament(tournamentId).orElseThrow(ResourceNotFoundException::new);
        var system = TournamentSystemFactory.create(context, tournament);
        system.finishTournament();
        tournament.setStatus(TournamentStatus.ENDED);
        tournamentRepository.save(tournament);
    }

    @Override
    public Tournament getById(String tournamentId) {
        return tournamentRepository.getById(tournamentId);
    }

    @Override
    public Tournament getByIdOrThrow(String tournamentId) {
        return findTournament(tournamentId).orElseThrow(
                () -> new ResourceNotFoundException("Not found tournament [" + tournamentId + "]"));
    }

    @Override
    public Optional<Tournament> findTournament(String tournamentId) {
        return tournamentRepository.findById(tournamentId);
    }

    @Override
    public void deleteTournament(String tournamentId) {
        tournamentRepository.deleteById(tournamentId);
    }

    @Override
    public void updateTournament(Tournament tournament) {
        if (tournament != null) {
            tournamentRepository.save(tournament);
        }
    }

    @Override
    public List<Tournament> getTournamentsByAccess(AccessOption accessOption) {
        return tournamentRepository.getAllByAccessOption(accessOption);
    }

    @Override
    public String getTournamentName(String tournamentId) {
        var tournament = tournamentRepository.getById(tournamentId);
        if (tournament == null) {
            return null;
        }
        return tournament.getName();
    }

    private void validateTournamentParticipants(Tournament tournament) {
        //        return TODO fix
    }

}
