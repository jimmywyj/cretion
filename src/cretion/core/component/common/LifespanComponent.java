package cretion.core.component.common;

import cretion.core.component.Component;
import cretion.states.GameState;
import cretion.utilities.CretionException;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;

public class LifespanComponent extends Component {
    private float lifespan;
    private float elapsed;
    private World world;
    private Body body;
    private boolean ended;

    public LifespanComponent(float _lifespan, World _world, Body _body) {
        lifespan = _lifespan;
        world = _world;
        body = _body;
        ended = false;
    }

    public void endLifespan() {
        if (!ended) {
            ended = true;
            elapsed = lifespan - 150;
        }
    }

    public void update(float _delta) {
        elapsed += _delta;
        if (elapsed >= lifespan) {
            GameState.toBeRemovedEntities.add(entity);
            if (world != null && body != null)
                world.removeBody(body);
        }
    }
}
