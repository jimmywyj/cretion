package cretion.states;

import twl.slick.BasicTwlGameState;

public abstract class AbstractState extends BasicTwlGameState {
    protected int ID;

    public AbstractState(int _ID) {
        ID = _ID;
    }

    @Override
    public int getID() {
        return ID;
    }
}
