package cretion.core.entity.player.components;

import cretion.core.component.Component;
import cretion.core.entity.player.PlayerEntity;
import cretion.utilities.CretionException;

public class PlayerDirectionComponent extends Component {
    public final static String LEFT = "left";
    public final static String RIGHT = "right";
    /*public final static String UP = "up";
    public final static String DOWN = "down";*/

    private PlayerEntity player;
    private String currentDirection;

    public PlayerDirectionComponent(PlayerEntity _player) {
        player = _player;
        currentDirection = RIGHT;
    }

    public void setDirection(String _direction) {
        currentDirection = _direction;
    }

    public String getDirection() {
        return currentDirection;
    }

    public void update(float _delta) throws CretionException {
        if (player.getComponent(PlayerMovableComponent.class).isGoingLeft()) {
            currentDirection = LEFT;
        } else if (player.getComponent(PlayerMovableComponent.class).isGoingRight()) {
            currentDirection = RIGHT;
        } /*else if (player.getComponent(PlayerMovableComponent.class).isGoingUp()) {
            currentDirection = UP;
        } else if (player.getComponent(PlayerMovableComponent.class).isGoingDown()) {
            currentDirection = DOWN;
        }*/
    }
}
