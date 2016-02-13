package cretion.core.entity.player.components;

import java.util.HashMap;
import java.util.Map;

import cretion.core.component.animation.AnimationComponent;

import cretion.core.component.Component;
import org.newdawn.slick.SlickException;

public class PlayerAnimationStateComponent extends Component {
    private Map<String, AnimationComponent> states;
    private AnimationComponent currentState;
    private PlayerStateComponent playerStateComponent;
    private PlayerDirectionComponent playerDirectionComponent;
    private String path;

    public PlayerAnimationStateComponent() {
        states = new HashMap<>();
        currentState = null;
        playerStateComponent = null;
        playerDirectionComponent = null;
        path = "";
    }

    public void setup() throws SlickException {
        playerStateComponent = entity.getComponent(PlayerStateComponent.class);
        playerDirectionComponent = entity.getComponent(PlayerDirectionComponent.class);
        for (Map.Entry<String, AnimationComponent> entry : states.entrySet()) {
            entry.getValue().setEntity(entity);
            entry.getValue().setup();
        }
    }

    public PlayerAnimationStateComponent setPath(String _path) {
        path = _path;
        return this;
    }

    public String getPath() {
        return path;
    }

    public PlayerAnimationStateComponent addState(String _state, AnimationComponent _animation) {
        _animation.setPath(path);
        states.put(_state, _animation);
        return this;
    }

    public PlayerAnimationStateComponent setCurrentState(String _state) {
        if (states.containsKey(_state)) {
            if (currentState != states.get(_state)) {
                currentState = states.get(_state);
                currentState.resetAnimation();
            }
        }
        return this;
    }

    public void update(float _delta) {
        setCurrentState(playerStateComponent.getState());
        currentState.setFlip(playerDirectionComponent.getDirection());
        currentState.update(_delta);
    }

    public void draw() throws SlickException {
        if (!path.equals("")) {
            currentState.draw();
        }
    }
}
