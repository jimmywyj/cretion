package cretion.core.entity.indicator;

import cretion.core.component.common.PositionComponent;
import cretion.core.component.common.LifespanComponent;

import cretion.states.GameState;
import cretion.utilities.CretionException;

import java.awt.*;
import org.newdawn.slick.Color;

public class FloatingIndicatorPrefab {
    public static void createPrefab(String _message, int _damage, Point _position, Dimension _dimension, Color _color)
            throws CretionException {
        FloatingIndicatorEntity prefab = new FloatingIndicatorEntity(_message, _damage, _color);
        prefab.addComponent(
                new PositionComponent(new Point(_position.x + (int) (_dimension.getWidth() / 2), _position.y)));
        prefab.addComponent(new LifespanComponent(8000, null, null));
        prefab.setup();
        GameState.toBeAddedEntities.add(prefab);
    }

    public static void createPrefab(int _damage, Point _position, Dimension _dimension, Color _color)
            throws CretionException {
        FloatingIndicatorEntity prefab = new FloatingIndicatorEntity(_damage, _color);
        prefab.addComponent(
                new PositionComponent(new Point(_position.x + (int) (_dimension.getWidth() / 2), _position.y)));
        prefab.addComponent(new LifespanComponent(8000, null, null));
        prefab.setup();
        GameState.toBeAddedEntities.add(prefab);
    }
}
