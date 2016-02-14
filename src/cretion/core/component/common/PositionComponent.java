package cretion.core.component.common;

import java.awt.Point;

import org.dyn4j.geometry.Vector2;

import cretion.core.component.Component;

public class PositionComponent extends Component {
    private float x;
    private float y;

    public PositionComponent() {
        this(0, 0);
    }

    public PositionComponent(float _x, float _y) {
        x = _x;
        y = _y;
    }

    public PositionComponent(Point _point) {
        x = _point.x;
        y = _point.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Point getPoint() {
        return new Point((int) x, (int) y);
    }

    public void setX(float _x) {
        x = _x;
    }

    public void setY(float _y) {
        y = _y;
    }

    public void setPoint(Point _point) {
        x = _point.x;
        y = _point.y;
    }

    public PositionComponent forward(Vector2 _direction, double _distance) {
        return new PositionComponent(
                new Point((int) (x + (_direction.x * _distance)), (int) (y + (_direction.y * _distance))));
    }
}
