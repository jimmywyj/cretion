package cretion.core.entity.indicator;

import cretion.core.component.common.PositionComponent;
import cretion.core.entity.Entity;
import cretion.core.component.common.LifespanComponent;
import cretion.memory.MemoryManager;
import org.newdawn.slick.Color;

public class FloatingIndicatorEntity extends Entity {
    public static final int PRIORITY = 200;
    private PositionComponent positionComponent;
    private String damage;
    private Color color;
    private float accelerator;
    private float opacity;

    public FloatingIndicatorEntity(String _message, int _damage, Color _color) {
        priority = PRIORITY;
        damage = String.valueOf(_damage) + " " + _message;
        color = _color;
        accelerator = 0.1f;
        opacity = 1.0f;
    }

    public FloatingIndicatorEntity(int _damage, Color _color) {
        damage = String.valueOf(_damage);
        color = _color;
        accelerator = 0.1f;
        opacity = 1.0f;
    }

    @Override
    public void setup() {
        super.setup();
        positionComponent = getComponent(PositionComponent.class);
        int factor = 1;
        if (MemoryManager.RANDOM.nextBoolean())
            factor = -factor;
        positionComponent.setX(positionComponent.getX() + (MemoryManager.RANDOM.nextInt() % 3) * factor);
    }

    @Override
    public void update(float _delta) {
        super.update(_delta);
        accelerator += 0.1f;
        opacity -= 0.01f;
        positionComponent.setY((positionComponent.getY() - accelerator));

        try {
            if (opacity <= 0) {
                getComponent(LifespanComponent.class).kill();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw() {
        super.draw();
        MemoryManager.OUTLINE_DAMAGE_FONT.drawString(
                positionComponent.getX() - 1 - MemoryManager.OUTLINE_DAMAGE_FONT.getWidth(damage) / 2,
                positionComponent.getY() + 1, damage, Color.black);
        MemoryManager.DAMAGE_FONT.drawString(positionComponent.getX() - MemoryManager.DAMAGE_FONT.getWidth(damage) / 2,
                positionComponent.getY(), damage, new Color(color.r, color.g, color.b, opacity));
    }
}