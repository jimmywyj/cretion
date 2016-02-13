package cretion.data;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class ProfileData {
    private String name;
    private int health;
    private int level;
    private int experience;
    private String character;
    private String map;
    private int spawnx;
    private int spawny;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int luck;
    private Map<Integer, ItemData> inventory;
    private Map<String, ItemData> equipment;

    public ProfileData(String _name) {
        name = _name;
        character = "";
        map = "";
        spawnx = 0;
        spawny = 0;
        strength = 0;
        dexterity = 0;
        intelligence = 0;
        luck = 0;
        inventory = new HashMap<>();
        equipment = new HashMap<>();
    }

    public String getCharacter() {
        return character;
    }

    public void setExperience(int _experience) {
        experience = _experience;
    }

    public void setLevel(int _level) {
        level = _level;
    }

    public Point getSpawn() {
        return new Point(spawnx, spawny);
    }

    public void setSpawn(Point _position) {
        spawnx = _position.x;
        spawny = _position.y;
    }

    public Integer getStatsFromEquipment(String _stat) {
        int sum = 0;
        for (ItemData item : equipment.values()) {
            sum += item.getValue(_stat);
        }
        return sum;
    }

    public String getCurrentMapData() {
        return map;
    }

    public void setCurrentMapData(String _map) {
        map = _map;
    }

    public String getNameData() {
        return name;
    }

    public int getHealthData() {
        return health;
    }

    public int getLevelData() {
        return level;
    }

    public int getExperienceData() {
        return experience;
    }

    public int getStrength() {
        return strength + getStatsFromEquipment("strength");
    }

    public int getDexterity() {
        return dexterity + getStatsFromEquipment("dexterity");
    }

    public int getIntelligence() {
        return intelligence + getStatsFromEquipment("intelligence");
    }

    public int getLuck() {
        return luck + getStatsFromEquipment("luck");
    }

    public boolean addItemToInventory(ItemData _itemData) {
        if (inventory.size() >= 35)
            return false;
        for (int i = 0; i < 35; i++) {
            if (!inventory.containsKey(i)) {
                inventory.put(i, _itemData);
                return true;
            }
        }
        return false;
    }

    public Map<Integer, ItemData> getInventory() {
        return inventory;
    }

    public Map<String, ItemData> getEquipment() {
        return equipment;
    }

    public ProfileData load() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("./data/profiles/" + name + ".data"))) {
                String line = br.readLine();
                while (line != null) {
                    if (line.contains("health=")) {
                        health = Integer.parseInt(line.substring("health=".length()));
                    } else if (line.contains("level=")) {
                        level = Integer.parseInt(line.substring("level=".length()));
                    } else if (line.contains("experience=")) {
                        experience = Integer.parseInt(line.substring("experience=".length()));
                    } else if (line.contains("map=")) {
                        map = line.substring("map=".length());
                    } else if (line.contains("spawnx=")) {
                        spawnx = Integer.parseInt(line.substring("spawnx=".length()));
                    } else if (line.contains("spawny=")) {
                        spawny = Integer.parseInt(line.substring("spawny=".length()));
                    } else if (line.contains("strength=")) {
                        strength = Integer.parseInt(line.substring("strength=".length()));
                    } else if (line.contains("dexterity=")) {
                        dexterity = Integer.parseInt(line.substring("dexterity=".length()));
                    } else if (line.contains("intelligence=")) {
                        intelligence = Integer.parseInt(line.substring("intelligence=".length()));
                    } else if (line.contains("luck=")) {
                        luck = Integer.parseInt(line.substring("luck=".length()));
                    } else if (line.contains("items=")) {
                        List<String> exploded = Arrays.asList(line.substring("items=".length()).split("\\s*,\\s*"));
                        for (String item : exploded) {
                            if (!item.equals("")) {
                                List<String> exploded2 = Arrays.asList(item.split("\\s*:\\s*"));
                                inventory.put(Integer.parseInt(exploded2.get(0)),
                                        DataManager.getItem(exploded2.get(1)));
                            }
                        }
                    } else if (line.contains("equipment=")) {
                        List<String> exploded = Arrays.asList(line.substring("equipment=".length()).split("\\s*,\\s*"));
                        for (String item : exploded) {
                            if (!item.equals("")) {
                                List<String> exploded2 = Arrays.asList(item.split("\\s*:\\s*"));
                                equipment.put(exploded2.get(0), DataManager.getItem(exploded2.get(1)));
                            }
                        }
                    } else if (line.contains("character=")) {
                        character = line.substring("character=".length());
                    }

                    line = br.readLine();
                }
            }
        } catch (Exception e) {
            System.err.println("Couldn't load profile data: " + e.getMessage());
            System.exit(-1);
        }
        return this;
    }

    public void save() {
        try {
            PrintWriter writer = new PrintWriter("./data/profiles/" + name + ".data", "UTF-8");
            writer.println("health=" + health);
            writer.println("level=" + level);
            writer.println("experience=" + experience);
            writer.println("character=" + character);
            writer.println("map=" + map);
            writer.println("spawnx=" + spawnx);
            writer.println("spawny=" + spawny);
            writer.println("strength=" + strength);
            writer.println("dexterity=" + dexterity);
            writer.println("intelligence=" + intelligence);
            writer.println("luck=" + luck);
            writer.print("items=");
            String itemsString = "";
            for (int i = 0; i < 35; i++) {
                if (!inventory.containsKey(i))
                    continue;
                itemsString += i + ":" + inventory.get(i).getName() + ",";
            }
            if (!itemsString.isEmpty())
                writer.print(itemsString.substring(0, itemsString.length() - 1));
            writer.println();
            writer.print("equipment=");
            String equipmentString = "";
            for (String key : equipment.keySet()) {
                equipmentString += key + ":" + equipment.get(key).getName() + ",";
            }
            if (!equipmentString.isEmpty())
                writer.print(equipmentString.substring(0, equipmentString.length() - 1));
            writer.println();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Couldn't save profile data: " + e.getMessage());
            System.exit(-1);
        }
    }
}
