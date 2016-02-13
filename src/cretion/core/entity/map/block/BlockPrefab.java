package cretion.core.entity.map.block;

import java.awt.Dimension;
import java.awt.Point;

import cretion.core.component.animation.SingleSpriteComponent;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.core.engine.PhysicsEngine;
import cretion.core.entity.Entity;

public class BlockPrefab {
    public static Dimension dimension = new Dimension(32, 32);

    public static Entity createPrefab(Point _position,
                                      String _path,
                                      Point _source) {
        return createPrefab(_position,
                dimension,
                _path,
                _source,
                null);
    }

    public static Entity createPrefab(Point _position,
                                      String _path,
                                      Point _source,
                                      PhysicsEngine _physicsEngine) {
        return createPrefab(_position,
                dimension,
                _path,
                _source,
                _physicsEngine);
    }

    public static Entity createPrefab(Point _position,
                                      Dimension _dimension,
                                      String _path,
                                      Point _source,
                                      PhysicsEngine _physicsEngine) {
        Entity prefab = new Entity();
        prefab.shouldUpdate(false);
        prefab.addComponent(new PositionComponent(_position.x, _position.y));
        prefab.addComponent(new DimensionComponent(_dimension.width, _dimension.height));
        prefab.addComponent(new SingleSpriteComponent(_path, _source, _dimension));
        if (_physicsEngine != null) {
            prefab.setPriority(1);
            prefab.addComponent(new BlockPhysicsComponent(_physicsEngine.getWorld()));
        }
        prefab.setup();
        return prefab;
    }
}
