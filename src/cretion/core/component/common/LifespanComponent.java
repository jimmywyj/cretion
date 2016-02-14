package cretion.core.component.common;

import cretion.core.component.Component;
import cretion.core.entity.Entity;
import cretion.states.GameState;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;

public class LifespanComponent extends Component {
    private float lifespan;
    private float elapsed;
    private World world;
    private Body body;

    public LifespanComponent(float _lifespan, World _world, Body _body) {
        lifespan = _lifespan;
        elapsed = 0.0f;
        world = _world;
        body = _body;
    }

    public void reset(float _lifespan, World _world, Body _body) {
        lifespan = _lifespan;
        elapsed = 0.0f;
        world = _world;
        body = _body;
    }

    public void kill() {
        elapsed = lifespan;
    }

    public boolean isDead() {
        return elapsed >= lifespan;
    }

    public void update(float _delta) {
        elapsed += _delta;
        if (isDead()) {
            GameState.toBeRemovedEntities.add(entity);
            if (world != null && body != null) {
                world.removeBody(body);
            }
        }
    }

    public static int CompareLifespan(Entity a, Entity b) {
        LifespanComponent lifespanA = a.getComponent(LifespanComponent.class);
        LifespanComponent lifespanB = b.getComponent(LifespanComponent.class);
        if (lifespanA.elapsed > lifespanB.elapsed) return -1;
        if (lifespanA.elapsed < lifespanB.elapsed) return 1;
        return 0;
    }
}
