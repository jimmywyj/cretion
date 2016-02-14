package cretion.core.entity.projectile;

import cretion.core.component.common.LifespanComponent;
import cretion.core.entity.Entity;
import cretion.core.entity.mob.MobFilter;
import cretion.core.entity.player.PlayerFilter;
import org.dyn4j.collision.Filter;

public class ProjectileFilter implements Filter {
    public Entity shooter;
    private Entity projectile;

    public ProjectileFilter(Entity _shooter, Entity _projectile) {
        shooter = _shooter;
        projectile = _projectile;
    }

    @Override
    public boolean isAllowed(Filter filter) {
        try {
            if (filter instanceof ProjectileFilter) {
                return false;
            } else if (filter instanceof MobFilter) {
                if (((MobFilter) filter).owner == shooter) {
                    return false;
                } else {
                    // TODO: Actually find out why new projectiles aren't being created
                    // projectile.getComponent(LifespanComponent.class).kill();
                    return true;
                }
            } else if (filter instanceof PlayerFilter) {
                if (((PlayerFilter) filter).owner == shooter) {
                    return false;
                } else {
                    projectile.getComponent(LifespanComponent.class).kill();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
