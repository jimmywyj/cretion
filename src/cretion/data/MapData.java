package cretion.data;

import tiled.core.Tile;
import tiled.core.TileLayer;
import tiled.core.TileSet;
import tiled.io.TMXMapReader;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class MapData {
    private String name;
    private List<TileData> tiles;
    private List<WarpData> warps;
    private Map<String, Integer> borders;
    private TMXMapReader reader;

    public List<TileData> getTiles() {
        return Collections.unmodifiableList(tiles);
    }

    public List<WarpData> getWarps() {
        return Collections.unmodifiableList(warps);
    }

    public int getBorder(String _border) {
        return borders.get(_border);
    }

    public String getName() {
        return name;
    }

    public MapData(String _name) {
        name = _name;
        tiles = new ArrayList<>();
        warps = new ArrayList<>();
        borders = new HashMap<>();
    }

    public MapData loadFromTMX() {
        reader = new TMXMapReader();
        try {
            tiled.core.Map map = reader.readMap(new File("data/maps/" + name + ".tmx").getAbsolutePath());
            map.getLayers().stream().forEach(layer -> {
                int startX = 0;
                int startY = 0;
                int endX = layer.getWidth();
                int endY = layer.getHeight();

                for (int x = startX; x < endX; ++x) {
                    for (int y = startY; y < endY; ++y) {
                        Tile tile = ((TileLayer) layer).getTileAt(x, y);
                        if (tile == null) continue;

                        Image image = tile.getImage();
                        if (image == null) continue;

                        TileSet tileSet = tile.getTileSet();
                        TileData tileData = new TileData();
                        String fileName = tile.getProperties().getProperty("filename");
                        if (fileName == null) {
                            String tileBmpFile = tileSet.getTilebmpFile();
                            int indexOfResources = tileBmpFile.indexOf("resources\\");
                            fileName = tileBmpFile.substring(indexOfResources);
                        }

                        String sourceProperty = tile.getProperties().getProperty("source");
                        Point source = new Point(0, 0);
                        if (sourceProperty != null) {
                            source = parsePoint(sourceProperty);
                        } else {
                            int tilesPerRow = tileSet.getTilesPerRow();
                            int row = tile.getId() % tilesPerRow;
                            int column = (int)Math.floor(tile.getId() / tilesPerRow);
                            source = new Point(row * tile.getWidth(), column * tile.getHeight());
                        }

                        tileData.setFilename(fileName);
                        tileData.setSourceX(source.x);
                        tileData.setSourceY(source.y);
                        tileData.setX(x * tile.getWidth());
                        tileData.setY(y * tile.getHeight());
                        tileData.setHasCollision(Boolean.parseBoolean(layer.getProperties().getProperty("collision")));
                        tiles.add(tileData);
                    }
                }
            });

            borders.put("borderXLeft", Integer.parseInt(map.getProperties().getProperty("borderXLeft")));
            borders.put("borderXRight", Integer.parseInt(map.getProperties().getProperty("borderXRight")));
            borders.put("borderYDown", Integer.parseInt(map.getProperties().getProperty("borderYDown")));
            borders.put("borderYUp", Integer.parseInt(map.getProperties().getProperty("borderYUp")));
        } catch (Exception e) {
            System.err.println("Couldn't load map data: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        return this;
    }

    public MapData load() {
        return loadFromTMX();
    }

    private Point parsePoint(String _data) {
        int x = 0, y = 0;
        try {
            String[] split = _data.split(",");
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
        } catch (Exception e) {
            System.err.println("Could not parse source position in tile properties (i.e: source=0,1)");
        }
        return new Point(x, y);
    }
}
