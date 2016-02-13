package cretion.gui.window;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cretion.core.component.common.DimensionComponent;
import cretion.memory.MemoryManager;
import cretion.states.GameState;
import cretion.utilities.CretionInputHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import cretion.core.component.common.PositionComponent;
import cretion.core.entity.item.DroppablePrefab;
import cretion.data.DataManager;
import cretion.data.ItemData;
import cretion.gui.CretionCamera;

public class InventoryWindow extends CretionWindow {
    public Integer toBeMovedSlot;
    private boolean bringToFront;

    public InventoryWindow(Point _point) {
        super(_point, new Dimension(170, 250));
        toBeMovedSlot = null;
        bringToFront = false;
        title = "Inventory";
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

        // Items background
        _g.setColor(new Color(50, 50, 50));
        _g.fillRect(relativeToWorldX(0.06f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.1f) - CretionCamera.CameraTranslateY, dimension.width - 20, dimension.height - 40);

        // Horizontal seperators
        _g.setColor(Color.white);
        for (int i = 0; i < 8; i++) {
            _g.drawLine(relativeToWorldX(0.06f) - CretionCamera.CameraTranslateX,
                    relativeToWorldY(0.1f) + (30 * i) - CretionCamera.CameraTranslateY,
                    relativeToWorldX(0.06f) + dimension.width - 20 - CretionCamera.CameraTranslateX,
                    relativeToWorldY(0.1f) + (30 * i) - CretionCamera.CameraTranslateY);
        }

        // Vertical seperators
        for (int i = 0; i < 6; i++) {
            _g.drawLine(relativeToWorldX(0.06f) + (30 * i) - CretionCamera.CameraTranslateX,
                    relativeToWorldY(0.1f) - CretionCamera.CameraTranslateY,
                    relativeToWorldX(0.06f) + (30 * i) - CretionCamera.CameraTranslateX,
                    relativeToWorldY(0.1f) + dimension.height - 40 - CretionCamera.CameraTranslateY);
        }

        // Items
        Map<String, ArrayList<PositionComponent>> toDraw = new HashMap<>();
        int horizontalIndex = 0;
        int verticalIndex = 0;
        for (int i = 0; i < 35; i++) {
            if (horizontalIndex >= 5) {
                horizontalIndex = 0;
                verticalIndex++;
            }

            // Compute position
            float x1 = relativeToWorldX(0.06f) + horizontalIndex * 30 - CretionCamera.CameraTranslateX;
            float y1 = relativeToWorldY(0.1f) + verticalIndex * 30 - CretionCamera.CameraTranslateY;
            float x2 = relativeToWorldX(0.06f) + horizontalIndex * 30 + 30 - CretionCamera.CameraTranslateX;
            float y2 = relativeToWorldY(0.1f) + verticalIndex * 30 + 30 - CretionCamera.CameraTranslateY;

            // Determine if it was clicked
            if (CretionInputHandler.lastClick != null) {
                if (CretionInputHandler.lastClick.x > x1 && CretionInputHandler.lastClick.x < x2
                        && CretionInputHandler.lastClick.y > y1 && CretionInputHandler.lastClick.y < y2) {
                    boolean didAction = false;
                    for (CretionWindow window : GameState.windows) {
                        if (window instanceof EquipmentWindow) {
                            // Equipment to inventory
                            String equipmentKey = ((EquipmentWindow) window).toBeMovedKey;
                            if (equipmentKey != null) {
                                ItemData equipmentItem = DataManager.getCurrentProfile().getEquipment()
                                        .get(equipmentKey);
                                if (equipmentItem != null) {
                                    if (DataManager.getCurrentProfile().getInventory().containsKey(i)) {
                                        ItemData inventoryItem = DataManager.getCurrentProfile().getInventory().get(i);
                                        if (DataManager.getEquipmentType(inventoryItem).equals(equipmentKey)) {
                                            DataManager.getCurrentProfile().getEquipment().put(equipmentKey,
                                                    inventoryItem);
                                            DataManager.getCurrentProfile().getInventory().put(i, equipmentItem);
                                        }
                                    } else {
                                        DataManager.getCurrentProfile().getEquipment().remove(equipmentKey,
                                                equipmentItem);
                                        DataManager.getCurrentProfile().getInventory().put(i, equipmentItem);
                                    }
                                }
                                ((EquipmentWindow) window).toBeMovedKey = null;
                                didAction = true;
                            }
                        }
                    }

                    if (!didAction) {
                        bringToFront = true;

                        if (toBeMovedSlot != null) {
                            ItemData firstItem = DataManager.getCurrentProfile().getInventory().get(toBeMovedSlot);
                            if (DataManager.getCurrentProfile().getInventory().containsKey(i)) {
                                ItemData secondItem = DataManager.getCurrentProfile().getInventory().get(i);
                                DataManager.getCurrentProfile().getInventory().put(toBeMovedSlot, secondItem);
                                DataManager.getCurrentProfile().getInventory().put(i, firstItem);
                            } else {
                                DataManager.getCurrentProfile().getInventory().remove(toBeMovedSlot, firstItem);
                                DataManager.getCurrentProfile().getInventory().put(i, firstItem);
                            }
                            toBeMovedSlot = null;
                        } else {
                            if (DataManager.getCurrentProfile().getInventory().containsKey(i)) {
                                toBeMovedSlot = i;
                            }
                        }
                    }
                    CretionInputHandler.lastClick = null;
                }
            }

            if (!DataManager.getCurrentProfile().getInventory().containsKey(i)) {
                horizontalIndex++;
                continue;
            }

            ItemData item = DataManager.getCurrentProfile().getInventory().get(i);
            String droppablePath = item.getDroppablePath();

            if (!MemoryManager.ITEM_IMAGES.containsKey(droppablePath)) {
                try {
                    MemoryManager.ITEM_IMAGES.put(droppablePath, new Image(droppablePath));
                } catch (Exception e) {
                    // Drown
                }
            }

            if (toBeMovedSlot != null && toBeMovedSlot == i) {
                x1 = GameState.container.getInput().getMouseX() - CretionCamera.CameraTranslateX - 12;
                y1 = GameState.container.getInput().getMouseY() - CretionCamera.CameraTranslateY - 12;
            }

            if (!toDraw.containsKey(droppablePath)) {
                toDraw.put(droppablePath, new ArrayList<>());
            }
            toDraw.get(droppablePath).add(new PositionComponent(x1, y1));

            horizontalIndex++;
        }

        for (String key : toDraw.keySet()) {
            Image image = MemoryManager.ITEM_IMAGES.get(key);
            image.startUse();
            for (PositionComponent point : toDraw.get(key)) {
                image.drawEmbedded(point.getX() + 1, point.getY() + 1, point.getX() + 29, point.getY() + 29, 0, 0, 24,
                        24);
            }
            image.endUse();
        }

        if (toBeMovedSlot != null && CretionInputHandler.lastClick != null) {
            if (CretionInputHandler.lastClick.x < position.x - CretionCamera.CameraTranslateX
                    || CretionInputHandler.lastClick.x > position.x - CretionCamera.CameraTranslateX + dimension.width
                    || CretionInputHandler.lastClick.y < position.y - CretionCamera.CameraTranslateY
                    || CretionInputHandler.lastClick.y > position.y - CretionCamera.CameraTranslateY
                            + dimension.height) {
                ItemData item = DataManager.getCurrentProfile().getInventory().get(toBeMovedSlot);
                DataManager.getCurrentProfile().getInventory().remove(toBeMovedSlot, item);
                DroppablePrefab.createPrefab(item, physics,
                        new Point(
                                (int) player.getComponent(PositionComponent.class).getX()
                                        + player.getComponent(DimensionComponent.class).getWidth() / 4,
                                (int) player.getComponent(PositionComponent.class).getY()));
                toBeMovedSlot = null;
            }
        }
    }
}
