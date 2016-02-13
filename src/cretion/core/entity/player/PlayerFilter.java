package cretion.core.entity.player;

import cretion.core.entity.item.DroppableFilter;
import cretion.core.entity.item.components.DroppablePhysicsComponent;
import cretion.core.entity.item.ItemEntity;
import cretion.core.entity.item.components.ItemFollowingComponent;
import cretion.core.entity.map.warp.WarpPhysicsComponent;
import cretion.core.entity.player.components.PlayerPhysicsComponent;
import cretion.core.entity.projectile.ProjectileFilter;
import cretion.states.GameState;
import org.dyn4j.collision.Filter;
import org.newdawn.slick.Input;

public class PlayerFilter implements Filter {
    public PlayerEntity owner = null;
    private boolean justPickedUp = false;

    public PlayerFilter(PlayerEntity _owner) {
        owner = _owner;
    }

    @Override
    public boolean isAllowed(Filter filter) {
        if (filter instanceof ProjectileFilter) {
            if (((ProjectileFilter) filter).shooter == owner) {
                return false;
            }
        } else if (filter instanceof WarpPhysicsComponent.WarpFilter) {
            try {
                if (GameState.container.getInput().isKeyPressed(Input.KEY_UP) &&
                        owner.getComponent(PlayerPhysicsComponent.class).isStill()) {
                    owner.getComponent(PlayerPhysicsComponent.class).resetVelocity();
                    owner.profileData.setCurrentMapData(((WarpPhysicsComponent.WarpFilter) filter).getDestination());
                    owner.profileData.setSpawn(((WarpPhysicsComponent.WarpFilter) filter).getDestinationPosition());
                    owner.game.enterState(1);
                }
            } catch (Exception e) {
                // Drown
            }
            return false;
        } else if (filter instanceof DroppableFilter) {
            try {
                if (GameState.container.getInput().isKeyDown(Input.KEY_Z)) {
                    if (!justPickedUp) {
                        justPickedUp = true;
                        if (((DroppableFilter) filter).wasPicked)
                            return false;
                        if (owner.profileData
                                .addItemToInventory(((ItemEntity) ((DroppableFilter) filter).owner).getItemData())) {
                            ((DroppableFilter) filter).wasPicked = true;
                            ((DroppableFilter) filter).owner.getComponent(DroppablePhysicsComponent.class)
                                    .setEnabled(false);
                            ((DroppableFilter) filter).owner.addComponent(new ItemFollowingComponent(owner));
                            ((DroppableFilter) filter).owner.getComponent(ItemFollowingComponent.class).setup();
                        }
                    }
                } else {
                    justPickedUp = false;
                }
            } catch (Exception e) {
                // Drown
            }
            return false;
        }
        return true;
    }
}