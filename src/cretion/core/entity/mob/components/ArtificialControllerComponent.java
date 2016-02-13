package cretion.core.entity.mob.components;

import cretion.core.component.Component;
import cretion.utilities.CretionException;

public class ArtificialControllerComponent extends Component {
    private boolean switcher;

    public ArtificialControllerComponent() throws CretionException {
        switcher = false;
    }

    public void setup() throws CretionException {
    }

    public void update(float _delta) throws CretionException {
        if (entity.getComponent(MobPhysicsComponent.class).isGrounded()) {
            if (entity.getComponent(MobPhysicsComponent.class).isCloseToEdge()) {
                switcher = !switcher;
            }

            if (switcher) {
                entity.getComponent(MobMovableComponent.class).setGoingLeft(true);
                entity.getComponent(MobMovableComponent.class).setGoingRight(false);
            } else {
                entity.getComponent(MobMovableComponent.class).setGoingRight(true);
                entity.getComponent(MobMovableComponent.class).setGoingLeft(false);
            }
        }
    }
}
