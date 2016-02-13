package cretion.core.entity.item.components;

import cretion.core.component.Component;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.core.entity.Entity;
import cretion.core.entity.item.components.DroppablePhysicsComponent;
import cretion.states.GameState;
import cretion.utilities.CretionException;

public class ItemFollowingComponent extends Component {
    private Entity target;
    private PositionComponent positionComponent;
    private PositionComponent targetPositionComponent;
    private DimensionComponent targetDimensionComponent;
    private float velocity;

    public ItemFollowingComponent(Entity _target) {
        target = _target;
        velocity = 1.0f;
    }

    public void setup() throws CretionException {
        positionComponent = entity.getComponent(PositionComponent.class);
        targetPositionComponent = target.getComponent(PositionComponent.class);
        targetDimensionComponent = target.getComponent(DimensionComponent.class);
    }

    public void update(float _delta) {
        if ((positionComponent.getX() > targetPositionComponent.getX() + targetDimensionComponent.getWidth() / 4 - 2
                && positionComponent.getX() < targetPositionComponent.getX() + targetDimensionComponent.getWidth() / 4
                        + 2)
                && positionComponent.getY() < targetPositionComponent.getY() + targetDimensionComponent.getHeight() / 4
                        + 2
                && positionComponent.getY() > targetPositionComponent.getY() + targetDimensionComponent.getHeight() / 4
                        - 2) {
            GameState.toBeRemovedEntities.add(entity);
            try {
                DroppablePhysicsComponent droppablePhysicsComponent = entity
                        .getComponent(DroppablePhysicsComponent.class);
                droppablePhysicsComponent.getWorld().removeBody(droppablePhysicsComponent.getBody());
            } catch (Exception e) {
                // Drown
            }
        }

        velocity += 0.08f;

        if (targetPositionComponent.getX() + targetDimensionComponent.getWidth() / 4 > positionComponent.getX() + 1) {
            positionComponent.setX(positionComponent.getX() + velocity);
        } else if (targetPositionComponent.getX() + targetDimensionComponent.getWidth() / 4 < positionComponent.getX()
                - 1) {
            positionComponent.setX(positionComponent.getX() - velocity);
        }

        if (targetPositionComponent.getY() + targetDimensionComponent.getHeight() / 4 > positionComponent.getY() + 1) {
            positionComponent.setY(positionComponent.getY() + velocity);
        } else if (targetPositionComponent.getY() + targetDimensionComponent.getHeight() / 4 < positionComponent.getY()
                - 1) {
            positionComponent.setY(positionComponent.getY() - velocity);
        }
    }
}
