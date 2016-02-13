package cretion;

import cretion.core.component.common.PositionComponent;
import cretion.data.DataManager;
import cretion.states.GameState;
import cretion.states.MainMenuState;
import cretion.states.MapEditorState;
import cretion.utilities.DebugInformation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;
import twl.slick.TwlStateBasedGame;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Cretion extends TwlStateBasedGame {
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    private final static int FPS = 60;

    public Cretion() {
        super("Cretion");
    }

    @Override
    public void initStatesList(GameContainer _container) throws SlickException {
        addState(new MainMenuState(0));
        addState(new GameState(1));
        addState(new MapEditorState(2));
    }

    @Override
    public boolean closeRequested() {
        if (DataManager.getCurrentProfile() != null) {
            DataManager.getCurrentProfile().setSpawn(
                    DebugInformation.player.getComponent(PositionComponent.class).getPoint());
            DataManager.getCurrentProfile().save();
        }
        return true;
    }

    @Override
    protected URL getThemeURL() {
        URL url = null;
        try {
            url = new File("resources/ui/theme.xml").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static void main(String[] args) {
        try {
            DataManager.load();
            Log.setVerbose(false);
            Cretion cretion = new Cretion();
            AppGameContainer container = new AppGameContainer(cretion);
            container.setDisplayMode(WIDTH, HEIGHT, false);
            container.setIcon("resources/logo.png");
            container.setShowFPS(false);
            container.setVerbose(false);
            container.setTargetFrameRate(FPS);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}