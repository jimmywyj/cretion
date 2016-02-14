package cretion.core.entity.player;

import cretion.core.component.animation.AnimationComponent;
import cretion.core.component.common.*;
import cretion.core.engine.PhysicsEngine;
import cretion.core.entity.Entity;
import cretion.core.entity.player.components.*;
import cretion.core.entity.projectile.ProjectilePool;
import cretion.core.entity.projectile.ProjectilePrefab;
import cretion.data.DataManager;
import cretion.data.ProfileData;
import cretion.gui.CretionCamera;
import cretion.states.GameState;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.util.*;

public class PlayerEntity extends Entity {
    public static final int PRIORITY = 100;
    public ProfileData profileData;
    public GameContainer gameContainer;
    public PhysicsEngine physicsEngine;
    public StateBasedGame game;

    private PlayerAnimationStateComponent baseAnimation;
    private Map<String, PlayerAnimationStateComponent> equipment;

    public PlayerEntity(ProfileData _profileData,
                        GameContainer _gameContainer,
                        StateBasedGame _game) {
        priority = PRIORITY;
        profileData = _profileData;
        gameContainer = _gameContainer;
        game = _game;

        baseAnimation = new PlayerAnimationStateComponent();
        baseAnimation.setPath(DataManager.getCharacters().get(_profileData.getCharacter()))
                .addState(PlayerStateComponent.STANDING,
                        new AnimationComponent(new Point(0, 0), new Point(4, 0), 200))
                .addState(PlayerStateComponent.JUMPING,
                        new AnimationComponent(new Point(0, 1), new Point(0, 1), 1))
                .addState(PlayerStateComponent.FALLING,
                        new AnimationComponent(new Point(0, 2), new Point(0, 2), 1))
                .addState(PlayerStateComponent.WALKING,
                        new AnimationComponent(new Point(0, 3), new Point(3, 3), 100))
                .addState(PlayerStateComponent.LEANING,
                        new AnimationComponent(new Point(0, 4), new Point(0, 4), 1))
                .addState(PlayerStateComponent.ATTACKING,
                        new AnimationComponent(new Point(0, 5), new Point(2, 5), 100).setLooping(false))
                .setCurrentState(PlayerStateComponent.STANDING)
                .setEntity(this);
        addComponent(baseAnimation);

        equipment = new LinkedHashMap<>();
        equipment.put("headwear", new PlayerAnimationStateComponent());
        equipment.put("bodywear", new PlayerAnimationStateComponent());
        equipment.put("handwear", new PlayerAnimationStateComponent());
        equipment.put("legwear", new PlayerAnimationStateComponent());
        equipment.put("shoewear", new PlayerAnimationStateComponent());
        equipment.put("weapon", new PlayerAnimationStateComponent());

        for (PlayerAnimationStateComponent animationState : equipment.values()) {
            animationState.setEntity(this);
        }

        addComponent(new NameComponent(_profileData.getNameData()));
        addComponent(new HealthComponent(_profileData.getHealthData()));
        addComponent(new ExperienceComponent(_profileData));
        addComponent(new PositionComponent(
                new Point((int) _profileData.getSpawn().getX(),
                          (int) _profileData.getSpawn().getY() - 20)));
        addComponent(new DimensionComponent(new Dimension(60, 60)));

        addComponent(new PlayerDirectionComponent(this));
        addComponent(new PlayerStateComponent(this));

        createEquipment("headwear");
        createEquipment("bodywear");
        createEquipment("handwear");
        createEquipment("legwear");
        createEquipment("shoewear");
        createEquipment("weapon");

        setup();
        setupAnimations();
    }

    public PlayerEntity(ProfileData _profileData,
                        GameContainer _gameContainer,
                        PhysicsEngine _physicsEngine,
                        StateBasedGame _game) {
        this(_profileData, _gameContainer, _game);
        physicsEngine = _physicsEngine;
        addComponent(new PlayerPhysicsComponent(this));
        addComponent(new PlayerMovableComponent(this));
        addComponent(new PlayerControllerComponent(this));
    }

    private void setupAnimations() {
        baseAnimation.invoke("setup");
        for (PlayerAnimationStateComponent animationState : equipment.values()) {
            animationState.invoke("setup");
        }
    }

    @Override
    public void update(float _delta) {
        super.update(_delta);

        for (Map.Entry<String, PlayerAnimationStateComponent> entry : equipment.entrySet()) {
            // If profile is wearing equipment at that spot
            if (profileData.getEquipment().containsKey(entry.getKey())) {
                // If its image path is different than the one in profile data
                // (changed equipment)
                if (!entry.getValue().getPath()
                        .equals(profileData.getEquipment().get(entry.getKey()).getSpritesheetPath())) {
                    equipment.put(entry.getKey(), new PlayerAnimationStateComponent());
                    createEquipment(entry.getKey());
                    equipment.get(entry.getKey()).setEntity(this);
                    setupAnimations();
                }
            } else { // Profile is no longer wearing that equipment
                entry.getValue().setPath("");
            }
            entry.getValue().invoke("update", _delta);
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (equipment.containsKey("headwear"))
            equipment.get("headwear").invoke("draw");
        if (equipment.containsKey("bodywear"))
            equipment.get("bodywear").invoke("draw");
        if (equipment.containsKey("handwear"))
            equipment.get("handwear").invoke("draw");
        if (equipment.containsKey("legwear"))
            equipment.get("legwear").invoke("draw");
        if (equipment.containsKey("weapon"))
            equipment.get("weapon").invoke("draw");
    }

    public PlayerAnimationStateComponent getEquipment(String _key) {
        if (equipment.containsKey(_key)) {
            return equipment.get(_key);
        }
        System.err.println("Could not find equipment " + _key);
        return null;
    }

    private void createEquipment(String _key) {
        if (profileData.getEquipment().containsKey(_key)) {
            this.getEquipment(_key).setPath(profileData.getEquipment().get(_key).getSpritesheetPath())
                    .addState(PlayerStateComponent.STANDING,
                            new AnimationComponent(new Point(0, 0), new Point(4, 0), 200))
                    .addState(PlayerStateComponent.JUMPING,
                            new AnimationComponent(new Point(0, 1), new Point(0, 1), 1))
                    .addState(PlayerStateComponent.FALLING,
                            new AnimationComponent(new Point(0, 2), new Point(0, 2), 1))
                    .addState(PlayerStateComponent.WALKING,
                            new AnimationComponent(new Point(0, 3), new Point(3, 3), 100))
                    .addState(PlayerStateComponent.LEANING,
                            new AnimationComponent(new Point(0, 4), new Point(0, 4), 1))
                    .addState(PlayerStateComponent.ATTACKING,
                            new AnimationComponent(new Point(0, 5), new Point(2, 5), 100).setLooping(false))
                    .setCurrentState(PlayerStateComponent.STANDING);
        }
    }

    public void attack() {
        int direction = -1;
        if (PlayerDirectionComponent.RIGHT.equals(getComponent(PlayerDirectionComponent.class).getDirection())) {
            direction = 1;
        }
        Entity projectile = ProjectilePool.getProjectile(getComponent(PositionComponent.class).getPoint(),
                "",
                physicsEngine,
                new Vector2(direction, 0),
                this,
                100);
        GameState.toBeAddedEntities.add(projectile);
    }

    public void shoot() {
        Vector2 mousePosition = new Vector2(gameContainer.getInput().getMouseX() - CretionCamera.CameraTranslateX,
                gameContainer.getInput().getMouseY() - CretionCamera.CameraTranslateY);
        Vector2 entityPosition = new Vector2(getComponent(PositionComponent.class).getX(),
                getComponent(PositionComponent.class).getY());
        Vector2 differenceVector = new Vector2(mousePosition.x - entityPosition.x,
                                               mousePosition.y - entityPosition.y);
        differenceVector.normalize();
        Entity projectile = ProjectilePool.getProjectile(getComponent(PositionComponent.class).getPoint(),
                ProjectilePrefab.FIREBALL,
                physicsEngine,
                differenceVector,
                this,
                2000);
        GameState.toBeAddedEntities.add(projectile);
    }
}
