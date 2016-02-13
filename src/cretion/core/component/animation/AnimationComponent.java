package cretion.core.component.animation;

import java.awt.Dimension;
import java.awt.Point;

import cretion.core.component.common.PositionComponent;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import cretion.core.component.Component;
import cretion.core.component.common.DimensionComponent;
import cretion.utilities.CretionException;
import cretion.utilities.ResourceLoader;

public class AnimationComponent extends Component {
    private Animation animation;
    private SpriteSheet spriteSheet;
    private boolean flipHorizontal;
    private boolean flipVertical;
    private String path;
    private Point start;
    private Point end;
    private int duration;
    private PositionComponent positionComponent;
    private boolean looping = true;

    public AnimationComponent(Point _start, Point _end, int _duration) {
        animation = null;
        spriteSheet = null;
        flipHorizontal = false;
        flipVertical = false;
        path = "";
        start = _start;
        end = _end;
        duration = _duration;
        positionComponent = null;
    }

    public AnimationComponent(String _path, Point _start, Point _end, int _duration) {
        animation = null;
        spriteSheet = null;
        flipHorizontal = false;
        flipVertical = false;
        path = _path;
        start = _start;
        end = _end;
        duration = _duration;
        positionComponent = null;
    }

    public void setup() throws CretionException {
        positionComponent = entity.getComponent(PositionComponent.class);
        spriteSheet = ResourceLoader.loadSpriteSheet(path,
                new Dimension(entity.getComponent(DimensionComponent.class).getWidth(),
                        entity.getComponent(DimensionComponent.class).getHeight()));
        animation = new Animation(spriteSheet, start.x, start.y, end.x, end.y, true, duration, false);
        animation.setLooping(looping);
    }

    public void setPath(String _path) {
        path = _path;
    }

    public AnimationComponent setLooping(boolean _looping) {
        looping = _looping;
        return this;
    }

    public void resetAnimation() {
        if (animation != null)
            animation.setCurrentFrame(0);
    }

    public void setFlip(String _direction) {
        if (_direction.equals("left")) {
            flipHorizontal = true;
        } else if (_direction.equals("right")) {
            flipHorizontal = false;
        }
    }

    public void update(float _delta) {
        animation.update((long) _delta);
    }

    public void draw() {
        animation.getCurrentFrame().getFlippedCopy(flipHorizontal, flipVertical).draw(positionComponent.getX(),
                positionComponent.getY());
    }
}
