package cretion.core.entity.mob.components;

import cretion.core.component.Component;
import cretion.utilities.CretionException;

public class MobDirectionComponent extends Component {
    public final static String LEFT = "left";
    public final static String RIGHT = "right";

    private MobMovableComponent mobMovableComponent;
    private String currentDirection;

    public MobDirectionComponent() {
        mobMovableComponent = null;
        currentDirection = RIGHT;
    }

    public void setup() throws CretionException {
        mobMovableComponent = entity.getComponent(MobMovableComponent.class);
    }

    public void setDirection(String _direction) {
        currentDirection = _direction;
    }

    public String getDirection() {
        return currentDirection;
    }

    public void update(float _delta) {
        if (mobMovableComponent.isGoingLeft()) {
            currentDirection = LEFT;
        } else if (mobMovableComponent.isGoingRight()) {
            currentDirection = RIGHT;
        }
    }
}
