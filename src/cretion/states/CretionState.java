package cretion.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import twl.slick.BasicTwlGameState;

public class CretionState extends BasicTwlGameState {
    protected int ID;

    public CretionState(int _ID) {
        ID = _ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    }
}
