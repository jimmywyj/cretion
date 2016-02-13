package cretion.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import cretion.core.component.common.PositionComponent;
import cretion.core.entity.Entity;

public class CretionCamera {
    public static final int BORDER_NOT_SET = -69696969;

    public static float CameraTranslateX = 0;
    public static float CameraTranslateY = 0;
    public static float CameraDeltaTranslateX = 0;
    public static float CameraDeltaTranslateY = 0;

    private static PositionComponent positionComponent;
    private static int borderXLeft;
    private static int borderXRight;
    private static int borderYDown;
    private static int borderYUp;

    static {
        positionComponent = null;
        borderXLeft = BORDER_NOT_SET;
        borderXRight = BORDER_NOT_SET;
        borderYDown = BORDER_NOT_SET;
        borderYUp = BORDER_NOT_SET;
    }

    public static void follow(Entity _entity) {
        follow(_entity.getComponent(PositionComponent.class));
    }

    public static void follow(PositionComponent _positionComponent) {
        if (borderXLeft == BORDER_NOT_SET || borderXRight == BORDER_NOT_SET 
                || borderYDown == BORDER_NOT_SET || borderYUp == BORDER_NOT_SET) {
            System.err.println("Borders are not set");
        }
        positionComponent = _positionComponent;
    }

    public static void translate(GameContainer _container, Graphics _g) {
        if (positionComponent == null) return;

        int translateX = -(int) positionComponent.getX() + _container.getWidth() / 2;
        int translateY = -(int) positionComponent.getY() + _container.getHeight() / 2;

        int leftCameraExtremity = (int) positionComponent.getX() - _container.getWidth() / 2;
        int rightCameraExtremity = (int) positionComponent.getX() + _container.getWidth() / 2;
        if (leftCameraExtremity < borderXLeft) {
            int distanceToMinLeft = borderXLeft - leftCameraExtremity;
            translateX = -(int) positionComponent.getX() + _container.getWidth() / 2 - distanceToMinLeft;
        } else if (rightCameraExtremity > borderXRight) {
            int distanceToMaxRight = borderXRight - rightCameraExtremity;
            translateX = -(int) positionComponent.getX() + _container.getWidth() / 2 - distanceToMaxRight;
        }

        int downCameraExtremity = (int) positionComponent.getY() + _container.getHeight() / 2;
        int upCameraExtremity = (int) positionComponent.getY() - _container.getHeight() / 2;
        if (downCameraExtremity > borderYDown) {
            int distanceToMaxDown = borderYDown - downCameraExtremity;
            translateY = -(int) positionComponent.getY() + _container.getHeight() / 2 - distanceToMaxDown;
        } else if (upCameraExtremity < borderYUp) {
            int distanceToMaxUp = borderYUp - upCameraExtremity;
            translateY = -(int) positionComponent.getY() + _container.getHeight() / 2 - distanceToMaxUp;
        }

        CameraDeltaTranslateX = CameraTranslateX - translateX;
        CameraDeltaTranslateY = CameraTranslateY - translateY;
        CameraTranslateX = translateX;
        CameraTranslateY = translateY;
        _g.translate(translateX, translateY);
    }

    public static void setBorderXLeft(int _borderXLeft) {
        borderXLeft = _borderXLeft;
    }

    public static void setBorderXRight(int _borderXRight) {
        borderXRight = _borderXRight;
    }

    public static void setBorderYDown(int _borderYDown) {
        borderYDown = _borderYDown;
    }
    
    public static void setBorderYUp(int _borderYUp) {
        borderYUp = _borderYUp;
    }
}
