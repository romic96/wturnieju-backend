package pl.wturnieju.model;

import pl.wturnieju.model.generic.GenericProfile;

public abstract class Duel extends Persistent {

    private GenericProfile firstPlayer;

    private GenericProfile secondPlayer;

    private DuelResult result;

}