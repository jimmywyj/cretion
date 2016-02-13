package cretion.core.entity.map.block;

import cretion.utilities.PhysicsHelper;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;

import cretion.core.component.Component;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.utilities.CretionException;
import org.dyn4j.geometry.Vector2;

public class BlockPhysicsComponent extends Component {
    private Body body;
    private World world;

    public BlockPhysicsComponent(World _world) {
        body = null;
        world = _world;
    }

    public void setup() throws CretionException {
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        DimensionComponent dimensionComponent = entity.getComponent(DimensionComponent.class);
        body = new Body();

        Vector2 blockDimension = new Vector2(dimensionComponent.getWidth(), dimensionComponent.getHeight());
        Vector2 convertedWorldDimension = PhysicsHelper.toWorld(blockDimension);

        Rectangle box = new Rectangle(convertedWorldDimension.x, convertedWorldDimension.y);
        body.addFixture(new BodyFixture(box));
        body.setMass(Mass.Type.INFINITE);

        Vector2 blockPosition = new Vector2(positionComponent.getX(), positionComponent.getY());
        Vector2 convertedWorldPosition = PhysicsHelper.toWorld(blockPosition);

        body.translate(convertedWorldPosition);
        world.addBody(body);
    }
}