package cretion.gui.window;

import cretion.gui.CretionCamera;
import cretion.memory.MemoryManager;
import cretion.utilities.DebugInformation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;

public class DebugWindow extends CretionWindow {
    public DebugWindow(Point _point) {
        super(_point, new Dimension(200, 200));
        title = "Debug";
    }

    public void render(Graphics _g) {
        super.render(_g);
        _g.setColor(Color.white);
        MemoryManager.MENU_FONT.drawString(relativeToWorldX(0.05f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.12f) - CretionCamera.CameraTranslateY,
                "Entities size: " + DebugInformation.entities.size());
        MemoryManager.MENU_FONT.drawString(relativeToWorldX(0.05f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.22f) - CretionCamera.CameraTranslateY,
                "To be added size: " + DebugInformation.toBeAddedEntities.size());
        MemoryManager.MENU_FONT.drawString(relativeToWorldX(0.05f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.32f) - CretionCamera.CameraTranslateY,
                "To be removed size: " + DebugInformation.toBeRemovedEntities.size());
        MemoryManager.MENU_FONT.drawString(relativeToWorldX(0.05f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.42f) - CretionCamera.CameraTranslateY,
                "World bodies: " + DebugInformation.world.getBodyCount());
        MemoryManager.MENU_FONT.drawString(relativeToWorldX(0.05f) - CretionCamera.CameraTranslateX,
                relativeToWorldY(0.52f) - CretionCamera.CameraTranslateY,
                "FPS: " + DebugInformation.container.getFPS());
    }
}
