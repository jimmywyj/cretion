package cretion.core.component.common;

import java.awt.Dimension;

import cretion.core.component.Component;

public class DimensionComponent extends Component {
    private int width;
    private int height;

    public DimensionComponent(int _width, int _height) {
        width = _width;
        height = _height;
    }

    public DimensionComponent(Dimension _dimension) {
        width = (int) _dimension.getWidth();
        height = (int) _dimension.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
