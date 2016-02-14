package cretion.states;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cretion.core.entity.map.MapFactory;
import cretion.core.entity.mob.MobPrefab;
import cretion.core.entity.player.PlayerEntity;
import cretion.core.entity.projectile.ProjectilePool;
import cretion.data.DataManager;
import cretion.data.MapData;
import cretion.data.ProfileData;
import cretion.gui.CretionBackground;
import cretion.gui.CretionOverlay;
import cretion.gui.window.CretionWindow;
import cretion.utilities.CretionInputHandler;
import cretion.utilities.DebugInformation;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import cretion.core.engine.Engine;
import cretion.core.engine.PhysicsEngine;
import cretion.core.entity.Entity;
import cretion.gui.CretionCamera;
import cretion.utilities.CretionException;

public class GameState extends CretionState {
    public static List<Entity> entities;
    public static Map<String, Engine> engines;
    public static ArrayList<CretionWindow> windows;
    public static List<Entity> toBeAddedEntities;
    public static List<Entity> toBeRemovedEntities;
    public static ArrayList<CretionWindow> toBeAddedWindows;
    public static ArrayList<CretionWindow> toBeRemovedWindows;
    public static GameContainer container;
    public static List<Entity> entititesToUpdate;

    public GameState(int _ID) {
        super(_ID);
        engines = new HashMap<>();
        entities = new ArrayList<>();
        toBeAddedEntities = new ArrayList<>();
        toBeRemovedEntities = new ArrayList<>();
        windows = new ArrayList<>();
        toBeAddedWindows = new ArrayList<>();
        toBeRemovedWindows = new ArrayList<>();
        entititesToUpdate = new ArrayList<>();

        // DEBUG
        DebugInformation.engines = engines;
        DebugInformation.entities = entities;
        DebugInformation.toBeAddedEntities = toBeAddedEntities;
        DebugInformation.toBeRemovedEntities = toBeRemovedEntities;
        DebugInformation.windows = windows;
    }

    @Override
    public void init(GameContainer _container, StateBasedGame _game) throws CretionException {
        container = _container;
    }

    @Override
    public void enter(GameContainer _container, StateBasedGame _game) throws SlickException {
        // Clear previous data
        clear();

        // Get data
        ProfileData profileData = DataManager.getCurrentProfile();
        MapData mapData = DataManager.getMap(profileData.getCurrentMapData());

        // Create physics engine
        PhysicsEngine physics = new PhysicsEngine();
        engines.put("physics", physics);

        // Create map
        MapFactory.create(mapData, physics);

        // Create mob test
        Entity mob = MobPrefab.createPrefab(new Point(500, 400), MobPrefab.BOT, _container, physics, _game);
        addEntity(mob);

        // Create player
        PlayerEntity player = new PlayerEntity(profileData, _container, physics, _game);
        addEntity(player);

        // Camera
        CretionCamera.setBorderXLeft(mapData.getBorder("borderXLeft"));
        CretionCamera.setBorderXRight(mapData.getBorder("borderXRight"));
        CretionCamera.setBorderYDown(mapData.getBorder("borderYDown"));
        CretionCamera.setBorderYUp(mapData.getBorder("borderYUp"));
        CretionCamera.follow(player);

        // Set up GUI
        CretionOverlay.setPlayer(player);
        CretionWindow.setDroppableTest(physics, _game, player);

        // DEBUG
        DebugInformation.player = player;
        DebugInformation.world = physics.getWorld();
        DebugInformation.container = _container;
    }

    @Override
    public void render(GameContainer _container, StateBasedGame _game, Graphics _g) throws SlickException {
        CretionCamera.translate(_container, _g);
        CretionBackground.render(_g);
        entities.stream().forEach(e -> e.draw());
        CretionOverlay.render(_g);
        windows.stream().forEach(w -> w.render(_g));
    }

    @Override
    public void update(GameContainer _container, StateBasedGame _game, int _delta) throws CretionException {
        updateEntityList();
        engines.values().stream().forEach(e -> e.update(_delta));
        entititesToUpdate.stream().forEach(e -> e.update(_delta));
        updateWindowList();
        windows.stream().forEach(w -> w.step());
        CretionInputHandler.update();
    }

    private void clear() {
        entities.clear();
        toBeAddedEntities.clear();
        toBeRemovedEntities.clear();
        toBeAddedWindows.clear();
        toBeRemovedWindows.clear();
        engines.clear();
        windows.clear();
        entititesToUpdate.clear();
    }

    private void updateEntityList() {
        toBeRemovedEntities.stream().forEach(e -> removeEntity(e));
        toBeRemovedEntities.clear();
        toBeAddedEntities.stream().forEach(e -> addEntity(e));
        toBeAddedEntities.clear();
    }

    private void updateWindowList() {
        toBeRemovedWindows.stream().forEach(w -> windows.remove(w));
        toBeRemovedWindows.clear();
        toBeAddedWindows.stream().forEach(w -> windows.add(w));
        toBeAddedWindows.clear();
    }

    private void addEntity(Entity entity) {
        entities.add(entity);
        if (entity.shouldUpdate()) {
            entititesToUpdate.add(entity);
        }
        sortEntities();
    }

    private void removeEntity(Entity entity) {
        entities.remove(entity);
        if (entity.shouldUpdate()) {
            entititesToUpdate.remove(entity);
        }
        sortEntities();
    }

    private void sortEntities() {
        entities.sort((a, b) -> Entity.ComparePriority(a, b));
    }
}
