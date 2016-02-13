package cretion.core.entity.mob;

import cretion.core.component.common.DimensionComponent;
import cretion.core.component.common.ExperienceComponent;
import cretion.core.component.common.HealthComponent;
import cretion.core.component.common.PositionComponent;
import cretion.core.entity.Entity;
import cretion.core.entity.indicator.FloatingIndicatorPrefab;
import cretion.core.entity.item.DroppableFilter;
import cretion.core.entity.player.PlayerEntity;
import cretion.core.entity.player.PlayerFilter;
import cretion.core.entity.projectile.ProjectileFilter;
import org.dyn4j.collision.Filter;

import java.awt.*;

public class MobFilter implements Filter {
    public Entity owner;
    private boolean gaveExp;

    public MobFilter(Entity _entity) {
        owner = _entity;
        gaveExp = false;
    }

    @Override
    public boolean isAllowed(Filter filter) {
        if (filter instanceof ProjectileFilter) {
            if (((ProjectileFilter) filter).shooter == owner) {
                return false;
            }

            try {
                owner.getComponent(HealthComponent.class).damage(2);
                FloatingIndicatorPrefab.createPrefab(2,
                        new Point((int) owner.getComponent(PositionComponent.class).getX(),
                                (int) owner.getComponent(PositionComponent.class).getY()),
                        new Dimension(owner.getComponent(DimensionComponent.class).getWidth(),
                                owner.getComponent(DimensionComponent.class).getHeight()),
                        org.newdawn.slick.Color.red);
                if (owner.getComponent(HealthComponent.class).isDead()
                        && ((ProjectileFilter) filter).shooter instanceof PlayerEntity && !gaveExp) {
                    ((ProjectileFilter) filter).shooter.getComponent(ExperienceComponent.class).gain(10);
                    FloatingIndicatorPrefab.createPrefab("EXP", 10,
                            new Point((int) ((ProjectileFilter) filter).shooter.getComponent(PositionComponent.class)
                                    .getX(),
                            (int) ((ProjectileFilter) filter).shooter.getComponent(PositionComponent.class).getY()),
                            new Dimension(
                                    ((ProjectileFilter) filter).shooter.getComponent(DimensionComponent.class)
                                            .getWidth(),
                                    ((ProjectileFilter) filter).shooter.getComponent(DimensionComponent.class)
                                            .getHeight()),
                            org.newdawn.slick.Color.green);
                    gaveExp = true;
                }
            } catch (Exception e) {
                // Drown
            }
        } else if (filter instanceof PlayerFilter) {
            try {
                ((PlayerFilter) filter).owner.getComponent(HealthComponent.class).damage(1);
                FloatingIndicatorPrefab.createPrefab(1,
                        new Point((int) ((PlayerFilter) filter).owner.getComponent(PositionComponent.class).getX(),
                                (int) ((PlayerFilter) filter).owner.getComponent(PositionComponent.class).getY()),
                        new Dimension(((PlayerFilter) filter).owner.getComponent(DimensionComponent.class).getWidth(),
                                ((PlayerFilter) filter).owner.getComponent(DimensionComponent.class).getHeight()),
                        org.newdawn.slick.Color.pink);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else if (filter instanceof DroppableFilter) {
            return false;
        }
        return true;
    }
}