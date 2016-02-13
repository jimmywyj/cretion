package cretion.core.component.animation;

import java.awt.Dimension;
import java.awt.Point;

import cretion.core.component.common.PositionComponent;
import org.newdawn.slick.Image;

import cretion.core.component.Component;
import cretion.utilities.ResourceLoader;

public class SingleSpriteComponent extends Component {
    private Image image;
    private String path;
    private Point point;
    private Dimension dimension;
    private PositionComponent positionComponent;

    public SingleSpriteComponent(String _path, Point _point, Dimension _dimension) {
        image = null;
        path = _path;
        point = _point;
        dimension = _dimension;
        positionComponent = null;
    }

    public void setup() {
        try {
            image = ResourceLoader.loadSpriteSheet(path, dimension).getSubImage(point.x, point.y,
                    (int) dimension.getWidth(), (int) dimension.getHeight());
        } catch (Exception e) {
            System.err.println("Error loading SpriteSheet: " + path);
        }
        positionComponent = entity.getComponent(PositionComponent.class);
    }

    public void draw() {
        image.draw(positionComponent.getX(), positionComponent.getY());
    }
}
