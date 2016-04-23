package cretion.utilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MemoryManager {
    public static Random RANDOM = null;
    public static TrueTypeFont OUTLINE_DAMAGE_FONT = null;
    public static TrueTypeFont DAMAGE_FONT = null;
    public static TrueTypeFont MENU_FONT = null;
    public static TrueTypeFont WINDOW_FONT = null;
    public static TrueTypeFont NAME_FONT = null;
    public static Map<String, Image> ITEM_IMAGES = null;

    static {
        RANDOM = new Random();
        OUTLINE_DAMAGE_FONT = new TrueTypeFont(new Font("Verdana", Font.PLAIN, 11), false);
        DAMAGE_FONT = new TrueTypeFont(new Font("Verdana", Font.PLAIN, 10), false);
        MENU_FONT = new TrueTypeFont(new Font("Verdana", Font.BOLD, 12), false);
        NAME_FONT = new TrueTypeFont(new Font("Verdana", Font.BOLD, 9), false);
        ITEM_IMAGES = new HashMap<>();
        WINDOW_FONT = new TrueTypeFont(new Font("Verdana", Font.BOLD, 10), false);
    }
}
