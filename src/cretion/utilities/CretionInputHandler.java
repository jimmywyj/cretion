package cretion.utilities;

import cretion.core.engine.PhysicsEngine;
import cretion.core.entity.item.DroppablePrefab;
import cretion.data.DataManager;
import cretion.gui.CretionCamera;
import cretion.gui.window.CretionWindow;
import cretion.gui.window.DebugWindow;
import cretion.gui.window.EquipmentWindow;
import cretion.gui.window.InventoryWindow;
import cretion.states.GameState;
import org.newdawn.slick.Input;

import java.awt.*;

public class CretionInputHandler {
    public static Point lastClick;

    public static void update() {
        Input input = GameState.container.getInput();
        boolean alreadyOpen = false;
        if (input.isKeyPressed(Input.KEY_HOME)) {
            for (CretionWindow window : GameState.windows) {
                if (window instanceof DebugWindow) {
                    GameState.toBeRemovedWindows.add(window);
                    alreadyOpen = true;
                }
            }
            if (!alreadyOpen) {
                GameState.toBeAddedWindows.add(new DebugWindow(new Point(0, 0)));
            }
        } else if (input.isKeyPressed(Input.KEY_END)) {
            // DEBUG
            try {
                DroppablePrefab.createPrefab(DataManager.getRandomItem(),
                        (PhysicsEngine) GameState.engines.get("physics"),
                        new Point(GameState.container.getInput().getMouseX(),
                                 GameState.container.getInput().getMouseY()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (input.isKeyPressed(Input.KEY_I)) {
            for (CretionWindow window : GameState.windows) {
                if (window instanceof InventoryWindow) {
                    GameState.toBeRemovedWindows.add(window);
                    alreadyOpen = true;
                }
            }
            if (!alreadyOpen) {
                GameState.toBeAddedWindows.add(new InventoryWindow(new Point(400, 100)));
            }
        } else if (input.isKeyPressed(Input.KEY_E)) {
            for (CretionWindow window : GameState.windows) {
                if (window instanceof EquipmentWindow) {
                    GameState.toBeRemovedWindows.add(window);
                    alreadyOpen = true;
                }
            }
            if (!alreadyOpen) {
                GameState.toBeAddedWindows.add(new EquipmentWindow(new Point(240, 100)));
            }
        }

        // Get mouse click position
        if (GameState.container.getInput().isMousePressed(1)) {
            float mousex = GameState.container.getInput().getMouseX() - CretionCamera.CameraTranslateX;
            float mousey = GameState.container.getInput().getMouseY() - CretionCamera.CameraTranslateY;
            lastClick = new Point((int) mousex, (int) mousey);
        } else {
            lastClick = null;
        }
    }
}
