package pl.wturnieju.tournament;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import pl.wturnieju.model.IProfile;
import pl.wturnieju.model.InvitationStatus;
import pl.wturnieju.model.ParticipantType;

@Data
public class Participant implements IProfile {

    private String id;

    private String name;

    private String tournamentId;

    private String shortName;

    private ParticipantStatus participantStatus;

    private InvitationStatus invitationStatus;

    private ParticipantType participantType;

    private String leaderId;

    private List<Member> members = new ArrayList<>();
}
