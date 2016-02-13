package cretion.utilities;

import org.dyn4j.geometry.Vector2;

public class PhysicsHelper {
    public static double scale = 12.0;

    public static double toWorld(double _value) {
        return _value / scale;
    }

    public static double toScreen(double _value) {
        return _value * scale;
    }

    public static Vector2 toWorld(Vector2 _point) {
        return new Vector2(_point.x / scale, _point.y / scale);
    }

    public static Vector2 toScreen(Vector2 _point) {
        return new Vector2(_point.x * scale, _point.y * scale);
    }
}