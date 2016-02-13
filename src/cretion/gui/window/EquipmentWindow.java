package cretion.gui.window;

import cretion.data.DataManager;
import cretion.data.ItemData;
import cretion.gui.CretionCamera;
import cretion.memory.MemoryManager;
import cretion.states.GameState;
import cretion.utilities.CretionInputHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.awt.Point;
import java.awt.Dimension;

public class EquipmentWindow extends CretionWindow {
    public String toBeMovedKey;
    private boolean bringToFront;

    public EquipmentWindow(Point _point) {
        super(_point, new Dimension(150, 230));
        toBeMovedKey = "";
        bringToFront = false;
        title = "Equipment";
    }

    public void step() {
        if (bringToFront) {
            int oldIndex = GameState.windows.indexOf(this);
            CretionWindow placeholder = GameState.windows.get(GameState.windows.size() - 1);
            GameState.windows.set(GameState.windows.size() - 1, this);
            GameState.windows.set(oldIndex, placeholder);
            bringToFront = false;
        }
    }

    public void render(Graphics _g) {
        super.render(_g);

        float basex = relativeToWorldX(0.04f) - CretionCamera.CameraTranslateX;
        float basey = relativeToWorldY(0.12f) - CretionCamera.CameraTranslateY;
        drawAndHandleEquipment(_g, "headwear", basex, basey);
        drawAndHandleEquipment(_g, "bodywear", basex, basey + 40);
        drawAndHandleEquipment(_g, "handwear", basex, basey + 80);
        drawAndHandleEquipment(_g, "legwear", basex, basey + 120);
        drawAndHandleEquipment(_g, "shoewear", basex, basey + 160);
        drawAndHandleEquipment(_g, "weapon", basex + 50, basey + 80);

        if (toBeMovedKey != null && DataManager.getCurrentProfile().getEquipment().containsKey(toBeMovedKey)) {
            float x1 = GameState.container.getInput().getMouseX() - CretionCamera.CameraTranslateX - 12;
            float y1 = GameState.container.getInput().getMouseY() - CretionCamera.CameraTranslateY - 12;
            float x2 = x1 + 29;
            float y2 = y1 + 29;
            MemoryManager.ITEM_IMAGES
                    .get(DataManager.getCurrentProfile().getEquipment().get(toBeMovedKey).getDroppablePath())
                    .draw(x1, y1, x2, y2, 0, 0, 24, 24);
        }

        MemoryManager.WINDOW_FONT.drawString(relativeToWorldX(0.3f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.10f) - CretionCamera.CameraTranslateY,
                "Strength: " + DataManager.getCurrentProfile().getStrength());
        MemoryManager.WINDOW_FONT.drawString(relativeToWorldX(0.3f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.19f) - CretionCamera.CameraTranslateY,
                "Dexterity: " + DataManager.getCurrentProfile().getDexterity());
        MemoryManager.WINDOW_FONT.drawString(relativeToWorldX(0.3f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.28f) - CretionCamera.CameraTranslateY,
                "Intelligence: " + DataManager.getCurrentProfile().getIntelligence());
        MemoryManager.WINDOW_FONT.drawString(relativeToWorldX(0.3f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.37f) - CretionCamera.CameraTranslateY,
                "Luck: " + DataManager.getCurrentProfile().getLuck());
    }

    private void drawAndHandleEquipment(Graphics _g, String _key, float _x, float _y) {
        _g.setColor(new Color(50, 50, 50));
        _g.fillRect(_x, _y, 30, 30);
        _g.setColor(Color.white);
        _g.drawRect(_x - 1, _y - 1, 31, 31);

        float x1 = _x + 1;
        float y1 = _y + 1;
        float x2 = _x + 29;
        float y2 = _y + 29;

        // Determine if it was clicked
        if (CretionInputHandler.lastClick != null) {
            if (CretionInputHandler.lastClick.x > x1 && CretionInputHandler.lastClick.x < x2
                    && CretionInputHandler.lastClick.y > y1 && CretionInputHandler.lastClick.y < y2) {
                boolean didAction = false;
                for (CretionWindow window : GameState.windows) {
                    if (window instanceof InventoryWindow) {
                        // Inventory to equipment
                        Integer inventorySlot = ((InventoryWindow) window).toBeMovedSlot;
                        if (inventorySlot != null) {
                            if (DataManager.getCurrentProfile().getInventory().containsKey(inventorySlot)) {
                                ItemData item = DataManager.getCurrentProfile().getInventory().get(inventorySlot);
                                if (_key.equals(DataManager.getEquipmentType(item))) {
                                    DataManager.getCurrentProfile().getInventory().remove(inventorySlot, item);
                                    ItemData oldEquipment = DataManager.getCurrentProfile().getEquipment().get(_key);
                                    if (oldEquipment != null) {
                                        DataManager.getCurrentProfile().getInventory().put(inventorySlot, oldEquipment);
                                    }
                                    DataManager.getCurrentProfile().getEquipment().put(_key, item);
                                    ((InventoryWindow) window).toBeMovedSlot = null;
                                    didAction = true;
                                }
                            }
                        }
                    }
                }
                if (!didAction) {
                    bringToFront = true;

                    if (!toBeMovedKey.isEmpty() && toBeMovedKey.equals(_key)) {
                        toBeMovedKey = "";
                    } else {
                        toBeMovedKey = _key;
                    }
                }
                CretionInputHandler.lastClick = null;
            }
        }

        if (DataManager.getCurrentProfile().getEquipment().containsKey(_key)) {
            ItemData item = DataManager.getCurrentProfile().getEquipment().get(_key);
            if (!MemoryManager.ITEM_IMAGES.containsKey(item.getDroppablePath())) {
                try {
                    MemoryManager.ITEM_IMAGES.put(item.getDroppablePath(), new Image(item.getDroppablePath()));
                } catch (Exception e) {
                    // Drown
                }
            }
            if (!_key.equals(toBeMovedKey))
                MemoryManager.ITEM_IMAGES.get(item.getDroppablePath()).draw(x1, y1, x2, y2, 0, 0, 24, 24);
        }
    }
}
