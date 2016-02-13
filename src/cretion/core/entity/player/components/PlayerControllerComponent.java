package cretion.core.entity.player.components;

import cretion.core.entity.player.PlayerEntity;
import org.newdawn.slick.Input;

import cretion.core.component.Component;
import cretion.utilities.CretionException;

public class PlayerControllerComponent extends Component {
    private PlayerEntity player;

    public boolean pressingUp;
    public boolean pressingDown;
    public boolean pressingLeft;
    public boolean pressingRight;

    public PlayerControllerComponent(PlayerEntity _player) {
        player = _player;
        pressingUp = false;
        pressingDown = false;
        pressingLeft = false;
        pressingRight = false;
    }

    public void update(float _delta) throws CretionException {
        Input input = player.gameContainer.getInput();
        if (input.isKeyPressed(Input.KEY_LALT)) {
            player.getComponent(PlayerMovableComponent.class).setJumping(true);
            pressingUp = true;
        } else {
            player.getComponent(PlayerMovableComponent.class).setJumping(false);
            pressingUp = false;
        }

        if (input.isKeyDown(Input.KEY_DOWN)) {
            player.getComponent(PlayerMovableComponent.class).setGoingDown(true);
            pressingDown = true;
        } else {
            player.getComponent(PlayerMovableComponent.class).setGoingDown(false);
            pressingDown = false;
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            player.getComponent(PlayerMovableComponent.class).setGoingUp(true);
            pressingUp = true;
        } else {
            player.getComponent(PlayerMovableComponent.class).setGoingUp(false);
            pressingUp = false;
        }

        if (input.isKeyDown(Input.KEY_LEFT)) {
            player.getComponent(PlayerMovableComponent.class).setGoingLeft(true);
            pressingLeft = true;
        } else {
            player.getComponent(PlayerMovableComponent.class).setGoingLeft(false);
            pressingLeft = false;
        }

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            player.getComponent(PlayerMovableComponent.class).setGoingRight(true);
            pressingRight = true;
        } else {
            player.getComponent(PlayerMovableComponent.class).setGoingRight(false);
            pressingRight = false;
        }

        if (input.isMouseButtonDown(0)) {
            player.shoot();
        }

        if (input.isKeyPressed(Input.KEY_LCONTROL)) {
            if (!player.getComponent(PlayerStateComponent.class).getState().equals(PlayerStateComponent.LEANING)) {
                player.getComponent(PlayerStateComponent.class).setState(PlayerStateComponent.ATTACKING);
            }
        }

        if (input.isKeyPressed(Input.KEY_LSHIFT)) {
            // TODO: Attack with spell
        }
    }
}
