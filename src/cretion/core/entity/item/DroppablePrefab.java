package cretion.core.entity.item;

import java.awt.Dimension;
import java.awt.Point;

import cretion.core.component.animation.SingleSpriteComponent;
import cretion.core.entity.item.components.DroppablePhysicsComponent;

import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.core.engine.PhysicsEngine;
import cretion.data.ItemData;
import cretion.states.GameState;

public class DroppablePrefab {
    public static void createPrefab(ItemData _itemData, PhysicsEngine _physicsEngine, Point _position) {
        ItemEntity prefab = new ItemEntity(_itemData);
        prefab.addComponent(new PositionComponent(_position));
        prefab.addComponent(new DimensionComponent(new Dimension(24, 24)));
        prefab.addComponent(new DroppablePhysicsComponent(_physicsEngine.getWorld()));
        prefab.addComponent(
                new SingleSpriteComponent(_itemData.getDroppablePath(), new Point(0, 0), new Dimension(24, 24)));
        prefab.setup();
        GameState.toBeAddedEntities.add(prefab);
    }
}