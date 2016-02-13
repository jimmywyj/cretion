package cretion.core.entity.player.components;

import cretion.core.component.Component;
import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.PositionComponent;
import cretion.core.entity.item.DroppableFilter;
import cretion.core.entity.map.warp.WarpPhysicsComponent.WarpFilter;
import cretion.core.entity.mob.MobFilter;
import cretion.core.entity.player.PlayerEntity;
import cretion.core.entity.player.PlayerFilter;
import cretion.core.entity.projectile.ProjectileFilter;
import cretion.utilities.CretionException;

import cretion.utilities.PhysicsHelper;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.RaycastResult;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.*;

import java.util.LinkedList;

public class PlayerPhysicsComponent extends Component {
    private PlayerEntity player;
    private Body body;

    public PlayerPhysicsComponent(PlayerEntity _player) {
        player = _player;
        body = new Body();

        Vector2 playerDimension = new Vector2(player.getComponent(DimensionComponent.class).getWidth() / 2,
                player.getComponent(DimensionComponent.class).getHeight() + 13);
        Vector2 convertedWorldDimension = PhysicsHelper.toWorld(playerDimension);

        Ellipse ellipse = Geometry.createEllipse(convertedWorldDimension.x, convertedWorldDimension.y);
        BodyFixture ellipseFixture = new BodyFixture(ellipse);
        ellipseFixture.setFriction(5.0);
        ellipseFixture.setFilter(new PlayerFilter(player));

        body.addFixture(ellipseFixture);
        body.setMass(Mass.Type.FIXED_ANGULAR_VELOCITY);

        Vector2 playerPosition = new Vector2(player.getComponent(PositionComponent.class).getX(),
                player.getComponent(PositionComponent.class).getY());
        Vector2 convertedWorldPosition = PhysicsHelper.toWorld(playerPosition);

        body.translate(convertedWorldPosition);

        getWorld().addBody(body);
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return player.physicsEngine.getWorld();
    }

    public boolean isStill() {
        return Math.round(body.getLinearVelocity().x) == 0 &&
                Math.round(body.getLinearVelocity().y) == 0;
    }

    public boolean isLeaning() {
        if (true)  return false;
        // TODO: Have some kind of leaning sprite or effect on walls?
        String direction = entity.getComponent(PlayerDirectionComponent.class).getDirection();
        int factor = 0;
        if (PlayerDirectionComponent.LEFT.equals(direction)) {
            factor = -1;
        } else if (PlayerDirectionComponent.RIGHT.equals(direction)) {
            factor = 1;
        }

        LinkedList<RaycastResult> results = new LinkedList<>();

        Vector2 playerPosition = new Vector2(player.getComponent(PositionComponent.class).getX(),
                player.getComponent(PositionComponent.class).getY());
        Vector2 convertedWorldPosition = PhysicsHelper.toWorld(playerPosition);

        Ray ray = new Ray(convertedWorldPosition, new Vector2(factor, 0));
        getWorld().raycast(ray, 3, true, true, results);
        int ignored = 0;
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getFixture().getFilter() instanceof ProjectileFilter
                    || results.get(i).getFixture().getFilter() instanceof WarpFilter
                    || results.get(i).getFixture().getFilter() instanceof MobFilter
                    || results.get(i).getFixture().getFilter() instanceof DroppableFilter) {
                ignored++;
            }
        }
        return !results.isEmpty() && ignored != results.size();
    }

    public void applyImpulse(Vector2 _impulse) {
        body.applyImpulse(_impulse);
    }

    public void resetVelocity() {
        body.setLinearVelocity(0, 0);
    }

    public void update(float _delta) throws CretionException {
        body.applyImpulse(new Vector2(body.getLinearVelocity().getNegative().x * _delta,
                body.getLinearVelocity().getNegative().y * _delta));
        Vector2 convertedScreenPosition = PhysicsHelper.toScreen(body.getTransform().getTranslation());
        player.getComponent(PositionComponent.class).setX((int) convertedScreenPosition.x);
        player.getComponent(PositionComponent.class).setY((int) convertedScreenPosition.y);
    }
}