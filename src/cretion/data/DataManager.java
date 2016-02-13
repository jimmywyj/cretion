package cretion.data;

import java.io.File;
import java.util.*;

import cretion.memory.MemoryManager;
import cretion.utilities.CretionException;

public class DataManager {
    static private ProfileData currentProfile;
    static private List<ProfileData> profiles;
    static private Map<String, String> characters;
    static private Map<String, MapData> maps;
    static private Map<String, List<ItemData>> items;

    static {
        currentProfile = null;
        profiles = new ArrayList<>();
        characters = new HashMap<>();
        maps = new HashMap<>();
        items = new HashMap<>();
    }

    public static void load() throws CretionException {
        loadWearables();
        loadMaps();
        loadEquipment();
        loadProfiles();
    }

    public static void loadProfiles() throws CretionException {
        String path = "./data/profiles";
        String file;
        File folder = new File(path);
        File[] files = folder.listFiles();

        if (files == null)
            throw new CretionException("Could not find profiles in " + path);

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                file = files[i].getName();
                if (file.endsWith(".data")) {
                    ProfileData profile = new ProfileData(file.replace(".data", "")).load();
                    if (profile != null) {
                        profiles.add(profile);
                    }
                }
            }
        }
    }

    public static void loadWearables() throws CretionException {
        String path = "./resources/characters/";
        String file;
        File folder = new File(path);
        File[] files = folder.listFiles();

        if (files == null)
            throw new CretionException("Could not find wearables in " + path);

        for (int j = 0; j < files.length; j++) {
            if (files[j].isFile()) {
                file = files[j].getName();
                if (file.endsWith(".png")) {
                    characters.put(file.replace(".png", ""), files[j].getPath());
                }
            }
        }
    }

    public static void loadMaps() throws CretionException {
        String path = "./data/maps";
        String file;
        File folder = new File(path);
        File[] files = folder.listFiles();

        if (files == null)
            throw new CretionException("Could not find maps in " + path);

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                file = files[i].getName();
                if (file.endsWith(".tmx")) {
                    MapData map = new MapData(file.replace(".tmx", "")).load();
                    if (map != null) {
                        maps.put(map.getName(), map);
                    }
                }
            }
        }
    }

    public static void loadEquipment() throws CretionException {
        String[] types = { "headwear", "bodywear", "legwear", "weapon" };

        for (int i = 0; i < types.length; i++) {
            items.put(types[i], new ArrayList<ItemData>());
            String path = "./data/equipment/" + types[i];
            String file;
            File folder = new File(path);
            File[] files = folder.listFiles();

            if (files == null)
                throw new CretionException("Could not find items in " + path);

            for (int j = 0; j < files.length; j++) {
                if (files[j].isFile()) {
                    file = files[j].getName();
                    if (file.endsWith(".data")) {
                        ItemData item = new ItemData(ItemData.EQUIPMENT, file.replace(".data", ""), files[j].getPath())
                                .load();
                        if (item != null) {
                            items.get(types[i]).add(item);
                        }
                    }
                }
            }
        }
    }

    public static void setCurrentProfile(String _name) {
        for (ProfileData profile : profiles) {
            if (profile.getNameData().equals(_name)) {
                currentProfile = profile;
            }
        }
    }

    public static ProfileData getCurrentProfile() {
        return currentProfile;
    }

    public static Map<String, String> getCharacters() {
        return characters;
    }

    public static List<ProfileData> getProfiles() {
        return Collections.unmodifiableList(profiles);
    }

    public static ItemData getRandomItem() {
        int index = 0;
        int listIndex = MemoryManager.RANDOM.nextInt(items.values().size());
        for (List<ItemData> list : items.values()) {
            if (list.size() == 0)
                continue;
            if (listIndex == index) {
                index = 0;
                int itemIndex = MemoryManager.RANDOM.nextInt(list.size());
                for (ItemData item : list) {
                    if (itemIndex == index) {
                        return item;
                    }
                    index++;
                }
            }
            index++;
        }
        return null;
    }

    public static String getEquipmentType(ItemData _item) {
        for (String type : items.keySet()) {
            for (ItemData item : items.get(type)) {
                if (item == _item) {
                    return type;
                }
            }
        }
        System.err.println("Equipment type was not found for item " + _item.getName());
        return null;
    }

    public static ItemData getItem(String _name) throws CretionException {
        for (List<ItemData> list : items.values()) {
            for (ItemData item : list) {
                if (item.getName().equals(_name))
                    return item;
            }
        }
        throw new CretionException("Invalid item asked to DataManager: " + _name);
    }

    public static MapData getMap(String _name) {
        return maps.get(_name);
    }

    /*public static void main(String[] args) {
        //
        // DataManager.loadEquipment(); for (List<ItemData> list :
        // items.values()) { for (ItemData item : list) { try {
        // System.out.println("Item: " + item.getName()); System.out.println(
        // "Droppable: " + item.getDroppablePath()); System.out.println(
        // "Spritesheet: " + item.getSpritesheetPath()); System.out.println(
        // "Strength: " + item.getValue("strength")); System.out.println(
        // "Dexterity: " + item.getValue("dexterity")); System.out.println(
        // "Intelligence: " + item.getValue("intelligence"));
        // System.out.println("Luck: " + item.getValue("luck"));
        // System.out.println("Defence: " + item.getValue("defence"));
        // System.out.println(); } catch (Exception e) { e.printStackTrace(); }
        // } }
        //

        try {
            DataManager.load();
            for (ProfileData profile : DataManager.getProfiles()) {
                System.out.println(profile.getNameData());
                for (Map.Entry<String, ItemData> entry : profile.getEquipment().entrySet()) {
                    System.out.println("Item: " + entry.getValue().getName());
                    System.out.println("Slot: " + entry.getKey());
                    //
                    //System.out.println("Droppable: " +
                    //item.getDroppablePath()); System.out.println(
                    //"Spritesheet: " + item.getSpritesheetPath());
                    //System.out.println("Strength: " +
                    //item.getValue("strength")); System.out.println(
                    //"Dexterity: " + item.getValue("dexterity"));
                    //System.out.println("Intelligence: " +
                    //item.getValue("intelligence")); System.out.println(
                    //"Luck: " + item.getValue("luck")); System.out.println(
                    //"Defence: " + item.getValue("defence"));
                    //
                    System.out.println();
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
