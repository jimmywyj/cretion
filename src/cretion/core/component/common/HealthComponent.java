package cretion.core.component.common;

import cretion.core.component.Component;
import cretion.core.entity.mob.components.MobPhysicsComponent;
import cretion.core.entity.player.PlayerEntity;
import cretion.core.entity.player.components.PlayerPhysicsComponent;
import cretion.states.GameState;

public class HealthComponent extends Component {
    private int originalHealth;
    private int currentHealth;

    public HealthComponent(int _health) {
        originalHealth = _health;
        currentHealth = originalHealth;
    }

    public int getHealth() {
        return currentHealth;
    }

    public int damage(int _damage) {
        currentHealth -= _damage;
        if (isDead()) {
            currentHealth = 0;
            GameState.toBeRemovedEntities.add(entity);
            try {
                if (entity instanceof PlayerEntity) {
                    PlayerPhysicsComponent playerPhysicsComponent = entity.getComponent(PlayerPhysicsComponent.class);
                    playerPhysicsComponent.getWorld().removeBody(playerPhysicsComponent.getBody());
                } else {
                    MobPhysicsComponent mobPhysicsComponent = entity.getComponent(MobPhysicsComponent.class);
                    mobPhysicsComponent.getWorld().removeBody(mobPhysicsComponent.getBody());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return currentHealth;
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }
}
