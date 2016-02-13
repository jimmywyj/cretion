package cretion.core.entity.mob.components;

import java.util.LinkedList;

import cretion.core.component.common.*;
import cretion.core.entity.mob.MobFilter;
import cretion.core.entity.projectile.ProjectileFilter;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.RaycastResult;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Ellipse;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Ray;
import org.dyn4j.geometry.Vector2;

import cretion.core.component.Component;
import cretion.utilities.CretionException;
import cretion.utilities.PhysicsHelper;

public class MobPhysicsComponent extends Component {
    private Body body;
    private World world;

    public MobPhysicsComponent(World _world) {
        body = null;
        world = _world;
    }

    public void setup() throws CretionException {
        body = new Body();

        Vector2 entityDimension = new Vector2(entity.getComponent(DimensionComponent.class).getWidth() / 2,
                entity.getComponent(DimensionComponent.class).getHeight() + 5);
        Vector2 convertedWorldDimension = PhysicsHelper.toWorld(entityDimension);

        Ellipse ellipse = Geometry.createEllipse(convertedWorldDimension.x, convertedWorldDimension.y);
        BodyFixture ellipseFixture = new BodyFixture(ellipse);
        ellipseFixture.setFriction(5.0);
        ellipseFixture.setFilter(new MobFilter(entity));

        body.addFixture(ellipseFixture);

        body.setMass(Mass.Type.FIXED_ANGULAR_VELOCITY);

        Vector2 entityPosition = new Vector2(entity.getComponent(PositionComponent.class).getX(),
                entity.getComponent(PositionComponent.class).getY());
        Vector2 convertedWorldPosition = PhysicsHelper.toWorld(entityPosition);

        body.translate(convertedWorldPosition);
        world.addBody(body);
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return world;
    }

    public boolean isJumping() {
        return body.getLinearVelocity().y < -1;
    }

    public boolean isFalling() {
        return body.getLinearVelocity().y > 2;
    }

    public boolean isGrounded() throws CretionException {
        LinkedList<RaycastResult> results = new LinkedList<>();

        Vector2 entityPosition = new Vector2(entity.getComponent(PositionComponent.class).getX(),
                entity.getComponent(PositionComponent.class).getY());
        Vector2 convertedWorldPosition = PhysicsHelper.toWorld(entityPosition);

        Ray ray = new Ray(convertedWorldPosition, new Vector2(0, 1));
        world.raycast(ray, 5, true, true, results);
        int bullets = 0;
        for (int i = 0; i < results.size(); i++)
            if (results.get(i).getFixture().getFilter() instanceof ProjectileFilter)
                bullets++;
        return results.size() > 0 && bullets != results.size();
    }

    public boolean isCloseToEdge() throws CretionException {
        LinkedList<RaycastResult> results = new LinkedList<>();

        Vector2 entityPosition = new Vector2(entity.getComponent(PositionComponent.class).getX(),
                entity.getComponent(PositionComponent.class).getY());
        Vector2 convertedWorldPosition = PhysicsHelper.toWorld(entityPosition);

        String direction = entity.getComponent(MobDirectionComponent.class).getDirection();
        double factor = 0;
        if (MobDirectionComponent.LEFT.equals(direction)) {
            factor = -0.5;
        } else if (MobDirectionComponent.RIGHT.equals(direction)) {
            factor = 0.5;
        }

        Ray ray = new Ray(convertedWorldPosition, new Vector2(factor, 1));
        world.raycast(ray, 5, true, true, results);
        return results.size() == 0;
    }

    public void applyImpulse(Vector2 _impulse) {
        body.applyImpulse(_impulse);
    }

    public void setLinearVelocity(Vector2 _velocity) {
        body.setLinearVelocity(_velocity);
    }

    public void setLinearVelocityX(double _x) {
        setLinearVelocity(new Vector2(_x, body.getLinearVelocity().y));
    }

    public void update(float _delta) throws CretionException {
        Vector2 convertedScreenPosition = PhysicsHelper.toScreen(body.getTransform().getTranslation());
        entity.getComponent(PositionComponent.class).setX((int) convertedScreenPosition.x);
        entity.getComponent(PositionComponent.class).setY((int) convertedScreenPosition.y);
    }
}