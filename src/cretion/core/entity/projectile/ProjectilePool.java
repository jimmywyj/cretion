package cretion.core.entity.projectile;

import cretion.core.component.common.LifespanComponent;
import cretion.core.engine.PhysicsEngine;
import cretion.core.entity.Entity;
import org.dyn4j.geometry.Vector2;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ProjectilePool {
    private static int MAX_SIZE = 100;
    private static int CLEAN_CHUNK_SIZE = 25;
    private static Map<Entity, Boolean> pool;

    static {
        pool = new HashMap<>(MAX_SIZE);
    }

    public static Entity getProjectile(Point _position,
                                String _path,
                                PhysicsEngine _physicsEngine,
                                Vector2 _direction,
                                Entity _owner,
                                int _lifespan) {
        for (Map.Entry<Entity, Boolean> entry : pool.entrySet()) {
            Entity key = entry.getKey();
            if (key.getComponent(LifespanComponent.class).isDead()) {
                entry.setValue(true);
            }
            if (entry.getValue()) {
                entry.setValue(false);
                ProjectilePrefab.resetPrefab(key,
                        _position,
                        _physicsEngine,
                        _direction,
                        _owner,
                        _lifespan);
                return key;
            }
        }

        Entity newPrefab = ProjectilePrefab.createPrefab(_position,
                _path,
                _physicsEngine,
                _direction,
                _owner,
                _lifespan);
        pool.put(newPrefab, false);
        if (pool.size() > MAX_SIZE) {
            pool.entrySet().stream()
                    .sorted((a, b) -> {
                        if (a.getValue() && b.getValue()) return 0;
                        else if (a.getValue()) return -1;
                        else if (b.getValue()) return 1;
                        return LifespanComponent.CompareLifespan(a.getKey(), b.getKey());
                    })
                    .limit(CLEAN_CHUNK_SIZE)
                    .forEach((e) -> e.getKey().getComponent(LifespanComponent.class).kill());
        }
        return newPrefab;
    }
}
