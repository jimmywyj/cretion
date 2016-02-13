package cretion.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cretion.core.component.Component;

public class Entity {
    protected Map<String, Component> components;
    protected int priority;
    private boolean shouldUpdate;

    public Entity() {
        components = new HashMap<>();
        priority = 0;
        shouldUpdate = true;
    }

    public void setup() {
        for (Map.Entry<String, Component> entry : components.entrySet()) {
            entry.getValue().invoke("setup");
        }
    }

    public void update(float _delta) {
        for (Map.Entry<String, Component> entry : components.entrySet()) {
            entry.getValue().invoke("update", _delta);
        }
    }

    public void draw() {
        for (Map.Entry<String, Component> entry : components.entrySet()) {
            entry.getValue().invoke("draw");
        }
    }

    public <T extends Component> T getComponent(Class<T> _type) {
        Component component = components.get(_type.getName());
        if (component == null) {
            System.err.println("Entity does not have component: " + _type.getSimpleName());
        }
        return _type.cast(component);
    }

    public Component[] getComponents() {
        List<Component> array = new ArrayList<>();
        for (Map.Entry<String, Component> entry : components.entrySet()) {
            array.add(entry.getValue());
        }
        Component[] sarray = new Component[array.size()];
        array.toArray(sarray);
        return sarray;
    }

    public void addComponent(Component _component) {
        _component.setEntity(this);
        components.put(_component.getId(), _component);
    }

    public <T extends Component> void removeComponent(Class<T> _type) {
        Component component = components.get(_type.getName());
        if (component != null) {
            removeComponent(component.getId());
        }
    }

    public void removeComponent(String _id) {
        if (components.containsKey(_id)) {
            components.remove(components.get(_id));
        }
    }

    public void overwrite(Entity _entity) {
        for (Component component : _entity.getComponents()) {
            component.setEntity(this);
            components.put(component.getClass().getName(), component);
        }
    }

    public int getPriority() {
        return priority;
    }

    public boolean shouldUpdate() {
        return shouldUpdate;
    }

    public boolean shouldUpdate(boolean _shouldUpdate) {
        return shouldUpdate = _shouldUpdate;
    }

    public void setPriority(int _priority) {
        priority = _priority;
    }

    public static int ComparePriority(Entity a, Entity b) {
        if (a.priority > b.priority) return 1;
        if (a.priority < b.priority) return -1;
        return 0;
    }
}
