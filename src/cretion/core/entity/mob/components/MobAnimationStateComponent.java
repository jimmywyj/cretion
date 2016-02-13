package cretion.core.entity.mob.components;

import java.util.HashMap;
import java.util.Map;

import cretion.core.component.animation.AnimationComponent;
import org.newdawn.slick.util.Log;

import cretion.core.component.Component;
import cretion.utilities.CretionException;

public class MobAnimationStateComponent extends Component {
    private Map<String, AnimationComponent> states;
    private AnimationComponent currentState;
    private MobStateComponent mobStateComponent;
    private MobDirectionComponent mobDirectionComponent;
    private String path;

    public MobAnimationStateComponent() {
        states = new HashMap<>();
        currentState = null;
        mobStateComponent = null;
        mobDirectionComponent = null;
        path = "";
    }

    public void setup() throws CretionException {
        mobStateComponent = entity.getComponent(MobStateComponent.class);
        mobDirectionComponent = entity.getComponent(MobDirectionComponent.class);
        for (Map.Entry<String, AnimationComponent> entry : states.entrySet()) {
            entry.getValue().setEntity(entity);
            entry.getValue().setup();
        }
    }

    public MobAnimationStateComponent setPath(String _path) {
        path = _path;
        return this;
    }

    public MobAnimationStateComponent addState(String _state, AnimationComponent _animation) {
        if (!path.isEmpty()) {
            _animation.setPath(path);
        }
        states.put(_state, _animation);
        return this;
    }

    public MobAnimationStateComponent setCurrentState(String _state) {
        if (states.containsKey(_state)) {
            currentState = states.get(_state);
        } else {
            Log.warn("invalid state (" + _state + ") was called in " + getClass().getSimpleName());
        }
        return this;
    }

    public void update(float _delta) {
        setCurrentState(mobStateComponent.getState());
        currentState.setFlip(mobDirectionComponent.getDirection());
        currentState.update(_delta);
    }

    public void draw() {
        currentState.draw();
    }
}
