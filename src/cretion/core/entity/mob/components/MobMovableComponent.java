package cretion.core.entity.mob.components;

import org.dyn4j.geometry.Vector2;

import cretion.core.component.Component;
import cretion.utilities.CretionException;

public class MobMovableComponent extends Component {
    private static final int MAX_JUMP_COUNT = 2;
    private int jumpCount;
    private boolean isPressingJump;
    private boolean isFalling;
    private boolean isGoingRight;
    private boolean isGoingLeft;

    public MobMovableComponent() {
        jumpCount = 0;
        isPressingJump = false;
        isFalling = false;
        isGoingRight = false;
        isGoingLeft = false;
    }

    public void update(float _delta) throws CretionException {
        if (isPressingJump && jumpCount < MAX_JUMP_COUNT) {
            entity.getComponent(MobPhysicsComponent.class).applyImpulse(new Vector2(0, -8));
            jumpCount++;
            isPressingJump = false;
            isFalling = false;
        }

        if (entity.getComponent(MobPhysicsComponent.class).isFalling()) {
            isFalling = true;
        } else if (isFalling && entity.getComponent(MobPhysicsComponent.class).isGrounded()) {
            isFalling = false;
            jumpCount = 0;
        }

        if (isGoingRight) {
            entity.getComponent(MobPhysicsComponent.class).setLinearVelocityX(4);
        }

        if (isGoingLeft) {
            entity.getComponent(MobPhysicsComponent.class).setLinearVelocityX(-4);
        }
    }

    public boolean isJumping() throws CretionException {
        return entity.getComponent(MobPhysicsComponent.class).isJumping();
    }

    public boolean isFalling() {
        return isFalling;
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
