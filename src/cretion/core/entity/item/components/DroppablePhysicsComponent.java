package cretion.core.entity.item.components;

import cretion.core.entity.item.DroppableFilter;
import cretion.utilities.PhysicsHelper;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.*;

import cretion.core.component.Component;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.utilities.CretionException;

public class DroppablePhysicsComponent extends Component {
    private PositionComponent positionComponent;
    private DimensionComponent dimensionComponent;
    private Body body;
    private World world;
    private boolean enabled;

    public DroppablePhysicsComponent(World _world) {
        positionComponent = null;
        dimensionComponent = null;
        body = null;
        world = _world;
        enabled = true;
    }

    public void setup() throws CretionException {
        positionComponent = entity.getComponent(PositionComponent.class);
        dimensionComponent = entity.getComponent(DimensionComponent.class);

        body = new Body();

        Vector2 droppableDimension = new Vector2(dimensionComponent.getWidth() / 2, dimensionComponent.getHeight() / 2);
        Vector2 convertedWorldDimension = PhysicsHelper.toWorld(droppableDimension);

        Rectangle rectangle = Geometry.createRectangle(convertedWorldDimension.x, convertedWorldDimension.y);
        BodyFixture ellipseFixture = new BodyFixture(rectangle);
        ellipseFixture.setFriction(5.0);
        ellipseFixture.setFilter(new DroppableFilter(entity));

        body.addFixture(ellipseFixture);

        body.setMass(Mass.Type.FIXED_ANGULAR_VELOCITY);

        Vector2 droppablePosition = new Vector2(positionComponent.getX(), positionComponent.getY() - 10);
        Vector2 convertedWorldPosition = PhysicsHelper.toWorld(droppablePosition);

        body.translate(convertedWorldPosition.x, convertedWorldPosition.y);
        world.addBody(body);

        applyImpulse(new Vector2(0, -8));
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return world;
    }

    public void setEnabled(boolean _enabled) {
        enabled = _enabled;
    }

    public void applyImpulse(Vector2 _impulse) {
        body.applyImpulse(_impulse);
    }

    public void update(float _delta) {
        if (enabled) {
            Vector2 convertedScreenPosition = PhysicsHelper.toScreen(body.getTransform().getTranslation());
            positionComponent.setX((int) convertedScreenPosition.x);
            positionComponent.setY((int) convertedScreenPosition.y + 10);
        }
    }
}
