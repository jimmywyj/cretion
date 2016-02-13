package cretion.core.engine;

import org.dyn4j.dynamics.World;

public class PhysicsEngine extends Engine {
    public static double MILLIS_TO_SECONDS = 1000.0;
    private World world;

    public PhysicsEngine() {
        world = new World();
        world.setGravity(World.ZERO_GRAVITY);
    }

    public World getWorld() {
        return world;
    }

    public void update(float _delta) {
        double seconds = _delta / MILLIS_TO_SECONDS;
        world.update(seconds);
    }
}