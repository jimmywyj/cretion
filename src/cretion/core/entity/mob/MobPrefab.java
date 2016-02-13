package cretion.core.entity.mob;

import java.awt.Dimension;
import java.awt.Point;

import cretion.core.entity.Entity;
import cretion.core.entity.mob.components.*;
import org.newdawn.slick.GameContainer;

import cretion.core.component.animation.AnimationComponent;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.HealthComponent;
import cretion.core.component.common.PositionComponent;
import cretion.core.engine.PhysicsEngine;
import cretion.utilities.CretionException;
import org.newdawn.slick.state.StateBasedGame;

public class MobPrefab {
    public static final int PRIORITY = 101;
    public static final String BOT = "resources/characters/bot.png";

    public static Entity createPrefab(Point _position, String _path, GameContainer _container,
            PhysicsEngine _physicsEngine, StateBasedGame _game) throws CretionException {
        return createPrefab(_position, new Dimension(49, 49), _path, _container, _physicsEngine, _game);
    }

    public static Entity createPrefab(Point _position, Dimension _dimension, String _path, GameContainer _container,
            PhysicsEngine _physicsEngine, StateBasedGame _game) throws CretionException {
        Entity prefab = new Entity();
        prefab.setPriority(PRIORITY);
        prefab.addComponent(new HealthComponent(1000));
        prefab.addComponent(new PositionComponent(_position));
        prefab.addComponent(new DimensionComponent(_dimension));
        prefab.addComponent(new MobPhysicsComponent(_physicsEngine.getWorld()));
        prefab.addComponent(new MobMovableComponent());
        prefab.addComponent(new MobDirectionComponent());
        prefab.addComponent(new MobStateComponent());
        prefab.addComponent(new ArtificialControllerComponent());
        prefab.addComponent(new MobAnimationStateComponent());
        prefab.getComponent(MobAnimationStateComponent.class)
                .addState(MobStateComponent.STANDING,
                        new AnimationComponent(_path, new Point(0, 0), new Point(4, 0), 200))
                .addState(MobStateComponent.JUMPING, new AnimationComponent(_path, new Point(0, 1), new Point(0, 1), 1))
                .addState(MobStateComponent.FALLING, new AnimationComponent(_path, new Point(0, 2), new Point(0, 2), 1))
                .addState(MobStateComponent.WALKING,
                        new AnimationComponent(_path, new Point(0, 3), new Point(3, 3), 100))
                .setCurrentState(MobStateComponent.STANDING);
        prefab.setup();
        return prefab;
    }
}
