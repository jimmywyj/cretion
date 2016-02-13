package cretion.core.entity.map;

import cretion.core.engine.PhysicsEngine;
import cretion.core.entity.map.block.BlockPrefab;
import cretion.core.entity.map.warp.WarpPrefab;
import cretion.data.WarpData;
import cretion.data.MapData;
import cretion.data.TileData;
import cretion.states.GameState;
import cretion.utilities.CretionException;

import java.awt.*;

public class MapFactory {
    public static void create(MapData _mapData, PhysicsEngine _physics) throws CretionException {
        for (TileData tile : _mapData.getTiles()) {
            Point point = new Point(tile.getX(), tile.getY());
            Point sourcePoint = new Point(tile.getSourceX(), tile.getSourceY());
            GameState.toBeAddedEntities.add(BlockPrefab.createPrefab(point, tile.getFilename(), sourcePoint,
                    (tile.getHasCollision()) ? _physics : null));
        }
        for (WarpData warp : _mapData.getWarps()) {
            Point point = new Point(warp.getX(), warp.getY());
            Point dPoint = new Point(warp.getDx(), warp.getDy());
            GameState.toBeAddedEntities.add(WarpPrefab.createPrefab(point, WarpPrefab.WARP, _physics, warp.getDestination(), dPoint));
        }
    }
}