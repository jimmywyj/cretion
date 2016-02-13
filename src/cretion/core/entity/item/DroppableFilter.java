package cretion.core.entity.item;

import cretion.core.entity.Entity;
import cretion.core.entity.map.warp.WarpPhysicsComponent;
import cretion.core.entity.mob.MobFilter;
import cretion.core.entity.player.PlayerFilter;
import cretion.core.entity.projectile.ProjectileFilter;
import org.dyn4j.collision.Filter;

public class DroppableFilter implements Filter {
    public Entity owner;
    public boolean wasPicked;

    public DroppableFilter(Entity _entity) {
        owner = _entity;
        wasPicked = false;
    }

    @Override
    public boolean isAllowed(Filter filter) {
        if (filter instanceof ProjectileFilter) {
            return false;
        } else if (filter instanceof WarpPhysicsComponent.WarpFilter) {
            return false;
        } else if (filter instanceof PlayerFilter) {
            return false;
        } else if (filter instanceof MobFilter) {
            return false;
        } else if (filter instanceof DroppableFilter) {
            return false;
        }
        return true;
    }
}
