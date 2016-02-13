package cretion.gui;

import cretion.core.component.common.ExperienceComponent;
import cretion.core.component.common.HealthComponent;
import cretion.core.entity.player.PlayerEntity;
import cretion.data.DataManager;
import cretion.memory.MemoryManager;
import cretion.utilities.CretionException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;

public class CretionOverlay {
    private static PlayerEntity player = null;
    private static final Point MENU_POSITION = new Point(0, 0);
    private static final Dimension MENU_DIMENSION = new Dimension(799, 50);
    private static final Point HP_POSITION = new Point(590, 10);
    private static final Dimension HP_DIMENSION = new Dimension(200, 10);
    private static final Point EXP_POSITION = new Point(2, 40);
    private static final Dimension EXP_DIMENSION = new Dimension(797, 10);

    public static void setPlayer(PlayerEntity _player) {
        player = _player;
    }

    public static void render(Graphics _g) throws CretionException {
        // Draw menu background
        _g.setColor(new Color(30, 30, 30));
        _g.fillRect(MENU_POSITION.x - CretionCamera.CameraTranslateX, MENU_POSITION.y - CretionCamera.CameraTranslateY,
                MENU_DIMENSION.width, MENU_DIMENSION.height);
        _g.setColor(Color.gray);
        _g.drawRect(MENU_POSITION.x + 1 - CretionCamera.CameraTranslateX,
                MENU_POSITION.y + 1 - CretionCamera.CameraTranslateY, MENU_DIMENSION.width - 1,
                MENU_DIMENSION.height - 1);

        // Draw name
        MemoryManager.MENU_FONT.drawString(5 - CretionCamera.CameraTranslateX, 5 - CretionCamera.CameraTranslateY,
                DataManager.getCurrentProfile().getNameData(), Color.white);

        // Draw level
        MemoryManager.MENU_FONT.drawString(5 - CretionCamera.CameraTranslateX, 20 - CretionCamera.CameraTranslateY,
                "Lvl. " + DataManager.getCurrentProfile().getLevelData(), Color.white);

        // Draw health
        _g.setColor(Color.white);
        _g.fillRect(HP_POSITION.x - CretionCamera.CameraTranslateX, HP_POSITION.y - CretionCamera.CameraTranslateY,
                HP_DIMENSION.width, HP_DIMENSION.height);
        _g.setColor(Color.red);
        _g.fillRect(HP_POSITION.x + 1 - CretionCamera.CameraTranslateX,
                HP_POSITION.y + 1 - CretionCamera.CameraTranslateY,
                (HP_DIMENSION.width - 2) * ((float) player.getComponent(HealthComponent.class).getHealth()
                        / (float) DataManager.getCurrentProfile().getHealthData()),
                HP_DIMENSION.height - 2);

        // Draw experience
        _g.setColor(Color.white);
        _g.fillRect(EXP_POSITION.x - CretionCamera.CameraTranslateX, EXP_POSITION.y - CretionCamera.CameraTranslateY,
                EXP_DIMENSION.width, EXP_DIMENSION.height);
        _g.setColor(Color.green);
        _g.fillRect(EXP_POSITION.x + 1 - CretionCamera.CameraTranslateX,
                EXP_POSITION.y + 1 - CretionCamera.CameraTranslateY,
                (EXP_DIMENSION.width - 1)
                        * ((float) player.getComponent(ExperienceComponent.class).getExperience() / 100),
                EXP_DIMENSION.height - 2);
    }
}
