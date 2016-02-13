package cretion.utilities;

import java.awt.Dimension;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ResourceLoader {
    public static SpriteSheet loadSpriteSheet(String _path, Dimension _tileDimension) {
        SpriteSheet spriteSheet = null;
        try {
            spriteSheet = new SpriteSheet(_path,
                    (int) _tileDimension.getWidth(),
                    (int) _tileDimension.getHeight());
        } catch (SlickException e) {
            e.printStackTrace();
        }
        return spriteSheet;
    }
}
