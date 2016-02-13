package cretion.core.component.common;

import cretion.memory.MemoryManager;

import cretion.core.component.Component;
import cretion.utilities.CretionException;

public class NameComponent extends Component {
    private String name;
    private PositionComponent positionComponent;
    private DimensionComponent dimensionComponent;

    public NameComponent(String _name) {
        name = _name;
        positionComponent = null;
        dimensionComponent = null;
    }

    public String getName() {
        return name;
    }

    public void setup() {
        positionComponent = entity.getComponent(PositionComponent.class);
        dimensionComponent = entity.getComponent(DimensionComponent.class);
    }

    public void draw() {
        MemoryManager.NAME_FONT.drawString(
                positionComponent.getX() + dimensionComponent.getWidth() / 2
                        - MemoryManager.NAME_FONT.getWidth(name) / 2,
                positionComponent.getY() - dimensionComponent.getHeight() / 4, name);
    }
}
