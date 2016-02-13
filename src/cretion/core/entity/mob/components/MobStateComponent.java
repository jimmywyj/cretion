package cretion.core.entity.mob.components;

import cretion.core.component.Component;
import cretion.utilities.CretionException;

public class MobStateComponent extends Component {
    public final static String STANDING = "standing";
    public final static String JUMPING = "jumping";
    public final static String FALLING = "falling";
    public final static String WALKING = "walking";

    private MobMovableComponent mobMovableComponent;
    private String currentState;

    public MobStateComponent() throws CretionException {
        mobMovableComponent = null;
        currentState = STANDING;
    }

    public void setup() throws CretionException {
        mobMovableComponent = entity.getComponent(MobMovableComponent.class);
    }

    public MobStateComponent setState(String _state) {
        currentState = _state;
        return this;
    }

    public String getState() {
        return currentState;
    }

    public void update(float _delta) throws CretionException {
        if (mobMovableComponent.isFalling()) {
            currentState = FALLING;
        } else if (mobMovableComponent.isJumping()) {
            currentState = JUMPING;
        } else if (mobMovableComponent.isGoingLeft() || mobMovableComponent.isGoingRight()) {
            currentState = WALKING;
        } else {
            currentState = STANDING;
        }
    }
}
