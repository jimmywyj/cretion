package cretion.core.entity.map.warp;

import cretion.core.component.animation.AnimationComponent;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.core.engine.PhysicsEngine;
import cretion.core.entity.Entity;
import cretion.utilities.CretionException;

import java.awt.*;

public class WarpPrefab {
    public static final int PRIORITY = 102;
    public static final String WARP = "resources/objects/warp.png";

    public static Entity createPrefab(Point _position, String _path, PhysicsEngine _physicsEngine, String _destination,
            Point _destinationPosition) throws CretionException {
        return createPrefab(_position, new Dimension(49, 49), _path, _physicsEngine, _destination,
                _destinationPosition);
    }

    public static Entity createPrefab(Point _position, Dimension _dimension, String _path, PhysicsEngine _physicsEngine,
            String _destination, Point _destinationPosition) throws CretionException {
        Entity prefab = new Entity();
        prefab.setPriority(PRIORITY);
        prefab.addComponent(new PositionComponent(_position.x, _position.y));
        prefab.addComponent(new DimensionComponent(_dimension.width, _dimension.height));
        prefab.addComponent(new AnimationComponent(_path, new Point(0, 0), new Point(5, 0), 200));
        prefab.addComponent(new WarpPhysicsComponent(_physicsEngine.getWorld(), _destination, _destinationPosition));
        prefab.setup();
        return prefab;
    }
}
