package pl.wturnieju.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.wturnieju.dto.TournamentConfigDTO;
import pl.wturnieju.dto.TournamentTemplateDto;
import pl.wturnieju.service.ITournamentCreatorService;

@RequiredArgsConstructor
@RestController
public class TournamentCreatorController implements ITournamentCreatorController {

    private final ITournamentCreatorService tournamentCreatorService;

    @Override
    public void createTournament(@RequestBody TournamentTemplateDto dto) {
        tournamentCreatorService.create(dto);
    }

    @Override
    public TournamentConfigDTO getTournamentConfig() {
        return new TournamentConfigDTO();
    }
}