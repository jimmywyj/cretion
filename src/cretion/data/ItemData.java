package cretion.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import cretion.utilities.CretionException;

public class ItemData {
    public static final String USABLE = "usable";
    public static final String EQUIPMENT = "equipment";

    private String type;
    private String name;
    private String path;
    private Map<String, Integer> values;

    public ItemData(String _type, String _name, String _path) {
        type = _type;
        name = _name;
        path = _path;
        values = new HashMap<>();

        if (type.equals(EQUIPMENT)) {
            values.put("strength", 0);
            values.put("dexterity", 0);
            values.put("intelligence", 0);
            values.put("luck", 0);
            values.put("defence", 0);
        }
    }

    public int getValue(String _key) {
        int value = -1;
        if (values.containsKey(_key)) {
            value = values.get(_key);
        } else {
            System.err.println("Key " + _key + " not found in ItemData " + name);
        }
        return value;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getSpritesheetPath() {
        return path.replaceFirst("data", "resources").replace("data", "png");
    }

    public String getDroppablePath() {
        return path.replaceFirst("data", "resources").replace("equipment", "drops").replace("data", "png");
    }

    public ItemData load() throws CretionException {
        if (!new File(getSpritesheetPath()).exists()) {
            throw new CretionException("Spritesheet " + name + " was not found at location: " + getSpritesheetPath());
        }

        if (!new File(getDroppablePath()).exists()) {
            throw new CretionException("Droppable image " + name + " was not found at location: " + getDroppablePath());
        }

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line = br.readLine();
                while (line != null) {
                    for (String key : values.keySet()) {
                        if (line.contains(key + "=")) {
                            values.put(key, Integer.parseInt(line.substring((key + "=").length())));
                        }
                    }
                    line = br.readLine();
                }
            }
        } catch (Exception e) {
            System.err.println("Couldn't load item data: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        return this;
    }
}
