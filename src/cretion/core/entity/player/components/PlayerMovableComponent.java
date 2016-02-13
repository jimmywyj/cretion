package cretion.core.entity.player.components;

import cretion.core.entity.player.PlayerEntity;
import org.dyn4j.geometry.Vector2;

import cretion.core.component.Component;
import cretion.utilities.CretionException;

public class PlayerMovableComponent extends Component {
    private static final int SPEED = 10;
    private static final int MAX_JUMP_COUNT = 1;
    private int jumpCount;
    private boolean isPressingJump;
    private boolean isFalling;
    private boolean isGoingRight;
    private boolean isGoingLeft;
    private boolean isGoingUp;
    private boolean isGoingDown;
    private PlayerEntity player;

    public PlayerMovableComponent(PlayerEntity _player) {
        player = _player;
        jumpCount = 0;
        isPressingJump = false;
        isFalling = false;
        isGoingRight = false;
        isGoingLeft = false;
        isGoingUp = false;
        isGoingDown = false;
    }

    public void update(float _delta) throws CretionException {
        if (isGoingUp) {
            player.getComponent(PlayerPhysicsComponent.class).applyImpulse(new Vector2(0, -SPEED * _delta));
        }

        if (isGoingDown) {
            player.getComponent(PlayerPhysicsComponent.class).applyImpulse(new Vector2(0, SPEED * _delta));
        }

        if (isGoingRight) {
            player.getComponent(PlayerPhysicsComponent.class).applyImpulse(new Vector2(SPEED * _delta, 0));
        }

        if (isGoingLeft) {
            player.getComponent(PlayerPhysicsComponent.class).applyImpulse(new Vector2(-SPEED * _delta, 0));
        }
    }

    public boolean isLeaning() {
        return player.getComponent(PlayerPhysicsComponent.class).isLeaning();
    }

    public void setJumping(boolean _isJumping) {
        isPressingJump = _isJumping;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public boolean isJumping() {
        // TODO: Implement jumping in top 2D
        return false;
    }

    public boolean isGoingUp() {
        return isGoingUp;
    }

    public void setGoingUp(boolean _isGoingUp) {
        isGoingUp = _isGoingUp;
    }

    public boolean isGoingDown() {
        return isGoingDown;
    }

    public void setGoingDown(boolean _isGoingDown) {
        isGoingDown = _isGoingDown;
    }

    public boolean isGoingRight() {
        return isGoingRight;
    }

    public void setGoingRight(boolean _isGoingRight) {
        isGoingRight = _isGoingRight;
    }

    public boolean isGoingLeft() {
        return isGoingLeft;
    }

    public void setGoingLeft(boolean _isGoingLeft) {
        isGoingLeft = _isGoingLeft;
    }
}
