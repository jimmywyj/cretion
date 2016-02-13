package cretion.core.component;

import cretion.core.entity.Entity;

public abstract class Component {
    protected Entity entity = null;
    private boolean update = true;

    public void disable() { update = false; }
    public void enable() { update = true;}
    public void setEntity(Entity _entity) {
        entity = _entity;
    }
    public String getId() {
        return getClass().getName();
    }

    public Object invoke(String _method) {
        if (!update) return null;
        try {
            return getClass().getMethod(_method).invoke(this);
        } catch (Exception e) {
            // Drown exception
        }
        return null;
    }

    public void invoke(String _method, float _delta) {
        if (!update) return;
        try {
            getClass().getMethod(_method, float.class).invoke(this, _delta);
        } catch (Exception e) {
            // Drown exception
        }
    }
}
