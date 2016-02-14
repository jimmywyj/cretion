package cretion.core.entity.projectile.components;

import cretion.core.entity.Entity;
import cretion.core.entity.projectile.ProjectileFilter;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Vector2;

import cretion.core.component.Component;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.utilities.CretionException;
import cretion.utilities.PhysicsHelper;

public class ProjectilePhysicsComponent extends Component {
    private PositionComponent positionComponent;
    private Body body;
    private World world;
    private Vector2 direction;
    private Entity shooter;

    public ProjectilePhysicsComponent(Vector2 _direction, World _world, Entity _shooter) {
        positionComponent = null;
        body = null;
        world = _world;
        direction = _direction;
        shooter = _shooter;
    }

    public void reset(Vector2 _direction, World _world, Entity _shooter) {
        positionComponent = null;
        body = null;
        world = _world;
        direction = _direction;
        shooter = _shooter;
    }

    public void setup() {
        positionComponent = entity.getComponent(PositionComponent.class);
        DimensionComponent dimensionComponent = entity.getComponent(DimensionComponent.class);

        body = new Body();

        double projectileRadius = ((double) dimensionComponent.getWidth() / 3);
        double convertedWorldRadius = PhysicsHelper.toWorld(projectileRadius);

        Circle circle = Geometry.createCircle(convertedWorldRadius);
        BodyFixture circleFixture = new BodyFixture(circle);
        circleFixture.setFilter(new ProjectileFilter(shooter, entity));

        body.addFixture(circleFixture);

        body.setMass(Mass.Type.FIXED_ANGULAR_VELOCITY);
        body.setBullet(true);
        body.setGravityScale(0);

        Vector2 projectilePosition = new Vector2(positionComponent.getX(), positionComponent.getY());
        Vector2 convertedWorldPosition = PhysicsHelper.toWorld(projectilePosition);

        body.translate(convertedWorldPosition);
        body.setLinearVelocity(new Vector2(direction.x * 999999999, direction.y * 999999999));

        world.addBody(body);
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return world;
    }

    public void update(float _delta) throws CretionException {
        Vector2 convertedScreenPosition = PhysicsHelper.toScreen(body.getTransform().getTranslation());
        positionComponent.setX((int) convertedScreenPosition.x);
        positionComponent.setY((int) convertedScreenPosition.y);
    }
}