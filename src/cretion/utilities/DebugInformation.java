package cretion.utilities;

import cretion.core.engine.Engine;
import cretion.core.entity.Entity;
import cretion.core.entity.player.PlayerEntity;
import cretion.gui.window.CretionWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dyn4j.dynamics.World;
import org.newdawn.slick.GameContainer;

public class DebugInformation {
    public static PlayerEntity player;
    public static Map<String, Engine> engines;
    public static List<Entity> entities;
    public static List<Entity> toBeAddedEntities;
    public static List<Entity> toBeRemovedEntities;
    public static ArrayList<CretionWindow> windows;
    public static World world;
    public static GameContainer container;
}
