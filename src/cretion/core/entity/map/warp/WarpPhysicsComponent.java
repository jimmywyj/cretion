package cretion.core.entity.map.warp;

import cretion.core.component.Component;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.utilities.CretionException;
import cretion.utilities.PhysicsHelper;
import org.dyn4j.collision.Filter;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import java.awt.*;

public class WarpPhysicsComponent extends Component {
    private Body body;
    private World world;
    private String destination;
    private Point destinationPosition;

    public class WarpFilter implements Filter {
        private String destination;

        public String getDestination() {
            return destination;
        }

        public Point getDestinationPosition() {
            return destinationPosition;
        }

        public WarpFilter(String _destination) {
            destination = _destination;
        }

        @Override
        public boolean isAllowed(Filter filter) {
            return false;
        }
    }

    public WarpPhysicsComponent(World _world, String _destination, Point _destinationPosition) {
        body = null;
        world = _world;
        destination = _destination;
        destinationPosition = _destinationPosition;
    }

    public void setup() throws CretionException {
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        DimensionComponent dimensionComponent = entity.getComponent(DimensionComponent.class);
        body = new Body();

        double convertedWarpHeight = PhysicsHelper.toWorld(dimensionComponent.getHeight());

        Rectangle box = new Rectangle(10, convertedWarpHeight);
        BodyFixture fixture = new BodyFixture(box);
        fixture.setFilter(new WarpFilter(destination));
        body.addFixture(fixture);
        body.setMass(Mass.Type.INFINITE);

        Vector2 warpPosition = new Vector2(positionComponent.getX(), positionComponent.getY());
        Vector2 convertedWorldPosition = PhysicsHelper.toWorld(warpPosition);

        body.translate(convertedWorldPosition.x + convertedWarpHeight / 2, convertedWorldPosition.y);
        world.addBody(body);
    }
}