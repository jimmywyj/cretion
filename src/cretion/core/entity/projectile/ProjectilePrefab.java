package cretion.core.entity.projectile;

import java.awt.Dimension;
import java.awt.Point;
import cretion.core.entity.Entity;
import cretion.core.component.common.LifespanComponent;
import cretion.core.entity.projectile.components.ProjectilePhysicsComponent;
import cretion.states.GameState;
import org.dyn4j.geometry.Vector2;

import cretion.core.component.animation.AnimationComponent;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.core.engine.PhysicsEngine;

public class ProjectilePrefab {
    public static final int PRIORITY = 101;
    public static final String FIREBALL = "resources/projectiles/fireball.png";

    public static Entity resetPrefab(Entity _entity,
                                     Point _position,
                                     PhysicsEngine _physicsEngine,
                                     Vector2 _direction,
                                     Entity _owner,
                                     int _lifespan) {
        _entity.setPriority(PRIORITY);
        PositionComponent forwardPositionComponent = new PositionComponent(_position)
                .forward(_direction, (new Dimension(50, 50).getWidth() / 4) * 3);
        _entity.getComponent(PositionComponent.class).setPoint(forwardPositionComponent.getPoint());
        _entity.getComponent(ProjectilePhysicsComponent.class).reset(_direction, _physicsEngine.getWorld(), _owner);
        _entity.setup();
        _entity.getComponent(LifespanComponent.class).reset(_lifespan, _physicsEngine.getWorld(),
                _entity.getComponent(ProjectilePhysicsComponent.class).getBody());
        return _entity;
    }

    public static Entity createPrefab(Point _position,
                                      String _path,
                                      PhysicsEngine _physicsEngine,
                                      Vector2 _direction,
                                      Entity _owner,
                                      int _lifespan) {
        Entity prefab = new Entity();
        prefab.setPriority(PRIORITY);
        prefab.addComponent(new PositionComponent(_position).forward(_direction, (new Dimension(50, 50).getWidth() / 4) * 3));
        prefab.addComponent(new DimensionComponent(new Dimension(50, 50)));
        prefab.addComponent(new ProjectilePhysicsComponent(_direction, _physicsEngine.getWorld(), _owner));
        prefab.addComponent(new AnimationComponent(_path, new Point(0, 0), new Point(3, 0), 200));
        prefab.setup();
        prefab.addComponent(new LifespanComponent(_lifespan, _physicsEngine.getWorld(),
                prefab.getComponent(ProjectilePhysicsComponent.class).getBody()));
        prefab.getComponent(AnimationComponent.class).setLooping(false);
        return prefab;
    }
}
