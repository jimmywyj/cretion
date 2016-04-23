package cretion.gui.window;

import cretion.core.engine.PhysicsEngine;
import cretion.core.entity.player.PlayerEntity;
import cretion.gui.CretionCamera;
import cretion.utilities.MemoryManager;
import cretion.states.GameState;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

public class CretionWindow {
    protected static PhysicsEngine physics = null;
    protected static StateBasedGame game = null;
    protected static PlayerEntity player = null;

    protected Point position;
    protected Dimension dimension;
    protected float lastMouseX;
    protected float lastMouseY;
    protected boolean dragging;
    protected String title = "Default";

    public static void setDroppableTest(PhysicsEngine _physics, StateBasedGame _game, PlayerEntity _player) {
        physics = _physics;
        game = _game;
        player = _player;
    }

    public CretionWindow(Point _position, Dimension _dimension) {
        position = _position;
        dimension = _dimension;
        lastMouseX = 0;
        lastMouseY = 0;
        dragging = false;
    }

    protected float relativeToWorldX(float _percentage) {
        return position.x + (dimension.width * _percentage);
    }

    protected float relativeToWorldY(float _percentage) {
        return position.y + (dimension.height * _percentage);
    }

    public void step() {
    }

    public void render(Graphics _g) {
        if (GameState.container.getInput().isMouseButtonDown(1)) {
            float mousex = GameState.container.getInput().getMouseX() - CretionCamera.CameraTranslateX;
            float mousey = GameState.container.getInput().getMouseY() - CretionCamera.CameraTranslateY;

            if (dragging) {
                position.x += (mousex - lastMouseX - CretionCamera.CameraDeltaTranslateX);
                position.y += (mousey - lastMouseY - CretionCamera.CameraDeltaTranslateY);
            }

            if (mousex > position.x - CretionCamera.CameraTranslateX
                    && mousex < position.x - CretionCamera.CameraTranslateX + dimension.width
                    && mousey > position.y - CretionCamera.CameraTranslateY
                    && mousey < position.y - CretionCamera.CameraTranslateY + 15) {
                dragging = true;
            }

            if (mousex > position.x - CretionCamera.CameraTranslateX + dimension.width - 14
                    && mousex < position.x - CretionCamera.CameraTranslateX + dimension.width - 2
                    && mousey > position.y - CretionCamera.CameraTranslateY + 1
                    && mousey < position.y - CretionCamera.CameraTranslateY + 13) {
                GameState.toBeRemovedWindows.add(this);
            }

            lastMouseX = mousex;
            lastMouseY = mousey;
        } else {
            dragging = false;
        }

        // Draw gui body
        _g.setColor(new Color(20, 20, 20));
        _g.fillRect(position.x - CretionCamera.CameraTranslateX, position.y - CretionCamera.CameraTranslateY,
                dimension.width, dimension.height);

        // Draw top bar
        _g.setColor(new Color(50, 50, 50));
        _g.fillRect(position.x - CretionCamera.CameraTranslateX, position.y - CretionCamera.CameraTranslateY,
                dimension.width, 15);

        // Drop closing button
        _g.setColor(Color.red);
        _g.fillRect(position.x - CretionCamera.CameraTranslateX + dimension.width - 14,
                position.y - CretionCamera.CameraTranslateY + 1, 12, 12);
        _g.setColor(Color.black);
        _g.drawRect(position.x - CretionCamera.CameraTranslateX + dimension.width - 14,
                position.y - CretionCamera.CameraTranslateY + 1, 12, 12);
        _g.setColor(Color.white);
        _g.drawLine(position.x - CretionCamera.CameraTranslateX + dimension.width - 12,
                position.y - CretionCamera.CameraTranslateY + 3,
                position.x - CretionCamera.CameraTranslateX + dimension.width - 3,
                position.y - CretionCamera.CameraTranslateY + 12);
        _g.drawLine(position.x - CretionCamera.CameraTranslateX + dimension.width - 3,
                position.y - CretionCamera.CameraTranslateY + 3,
                position.x - CretionCamera.CameraTranslateX + dimension.width - 12,
                position.y - CretionCamera.CameraTranslateY + 12);

        // Draw title
        MemoryManager.WINDOW_FONT.drawString(position.x + 4 - CretionCamera.CameraTranslateX,
                position.y + 2 - CretionCamera.CameraTranslateY, title, Color.white);
    }
}
