package cretion.gui;

import cretion.utilities.CretionException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class CretionBackground {
    private static Image background;

    static {
        try {
            background = new Image("resources/backgrounds/hills.png");
        } catch (Exception e) {
            System.err.println("Can't find background resource.");
        }
    }

    public static void render(Graphics _g) throws CretionException {
        // Draw menu background
        _g.drawImage(background, -CretionCamera.CameraTranslateX, -CretionCamera.CameraTranslateY);
    }
}
