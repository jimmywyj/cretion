package cretion.core.entity.player.components;

import cretion.core.component.Component;
import cretion.core.entity.player.PlayerEntity;

public class PlayerStateComponent extends Component {
    public final static String STANDING = "standing";
    public final static String JUMPING = "jumping";
    public final static String FALLING = "falling";
    public final static String WALKING = "walking";
    public final static String LEANING = "leaning";
    public final static String ATTACKING = "attacking";

    private PlayerEntity player;

    private String currentState;
    private float attackingElapsed;
    private boolean attacked;

    public PlayerStateComponent(PlayerEntity _player) {
        player = _player;
        currentState = STANDING;
        attackingElapsed = 0;
        attacked = false;
    }

    public PlayerStateComponent setState(String _state) {
        currentState = _state;
        return this;
    }

    public String getState() {
        return currentState;
    }

    public void update(float _delta) {
        if (currentState.equals(ATTACKING)) {
            attackingElapsed += _delta;
            if (attackingElapsed > 150) {
                currentState = STANDING;
                attacked = false;
            } else if (!attacked) {
                player.attack();
                attacked = true;
            }
            return;
        } else {
            attackingElapsed = 0;
        }

        if (player.getComponent(PlayerMovableComponent.class).isLeaning()) {
            currentState = LEANING;
        } else if (player.getComponent(PlayerMovableComponent.class).isFalling()) {
            currentState = FALLING;
        } else if (player.getComponent(PlayerMovableComponent.class).isJumping()) {
            currentState = JUMPING;
        } else if (player.getComponent(PlayerMovableComponent.class).isGoingLeft()
                || player.getComponent(PlayerMovableComponent.class).isGoingRight()
                || player.getComponent(PlayerMovableComponent.class).isGoingUp()
                || player.getComponent(PlayerMovableComponent.class).isGoingDown()) {
            currentState = WALKING;
        } else if (!currentState.equals(ATTACKING)) {
            currentState = STANDING;
        }
    }
}
