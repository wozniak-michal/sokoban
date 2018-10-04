package com.jsoko;

import com.badlogic.gdx.utils.Array;
import com.jsoko.objects.GameObject;
import com.jsoko.objects.gameobjects.Crate;
import com.jsoko.objects.gameobjects.Floor;
import com.jsoko.objects.gameobjects.Place;
import com.jsoko.objects.gameobjects.Sokoban;
import com.jsoko.objects.gameobjects.Wall;

public class Map {

    private final int LAYERS = 5;

    private final char FLOOR_ID     = ' ';
    private final char WALL_ID      = '#';
    private final char PLACE_ID     = '.';
    private final char CRATE_ID     = '$';
    private final char SOKOBAN_ID   = '@';
    private final char CRATE_ON_PLACE_ID = '*';
    private final char SOKOBAN_ON_PLACE_ID = '+';

    private int mapWidth, mapHeight;
    private final Array<MapLayer> mapLayers;

    public Map() {
        this.mapLayers = new Array<>(LAYERS);

        for (int i = 0; i < LAYERS; i++) {
            this.mapLayers.add(new MapLayer());
        }
    }

    public Array<MapLayer> getMapLayers() {
        return mapLayers;
    }

    public MapLayer getLayer(int id) {
        return mapLayers.get(id);
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void createFromLevel(Level level) {
        mapWidth = level.getWidth();
        mapHeight = level.getHeight();

        char[] mapData = level.getMapData().toCharArray();
        createLayers(mapData);
    }

    private void createLayers(char[] mapData) {
        for (int layer = 0; layer < LAYERS; layer++) {
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {

                    char id = mapData[x + y * mapWidth];
                    float X = x * Constants.TILE_SIZE;
                    float Y = y * Constants.TILE_SIZE;

                    // drawing from bottom left, have to flip Y
                    addObject(layer, id, X, -Y);
                }
            }
        }
    }

    private void addObject(int layer, char id, float x, float y) {

        if (id == 'x') return;

        if (layer == MapLayer.FLOOR) {
            addToLayer(MapLayer.FLOOR, new Floor(Assets.getAtlasRegion(Constants.TILE_FLOOR_ASSET_ID), x, y));

        } else if (layer == MapLayer.WALL && id == WALL_ID) {
            addToLayer(MapLayer.WALL, new Wall(Assets.getAtlasRegion(Constants.TILE_WALL_ASSET_ID), x, y));

        } else if (layer == MapLayer.PLACE && id == PLACE_ID) {
            addToLayer(MapLayer.PLACE, new Place(Assets.getAtlasRegion(Constants.TILE_PLACE_ASSET_ID), x, y));

        } else if (layer == MapLayer.CRATE && id == CRATE_ID) {
            addToLayer(MapLayer.CRATE, new Crate(Assets.getAtlasRegions(Constants.TILE_CRATE_ASSET_ID), x, y));

        } else if (layer == MapLayer.SOKOBAN && id == SOKOBAN_ID) {
            addToLayer(MapLayer.SOKOBAN, new Sokoban(x, y));

        } else if (layer == MapLayer.CRATE && id == CRATE_ON_PLACE_ID) {
            addToLayer(MapLayer.PLACE, new Place(Assets.getAtlasRegion(Constants.TILE_PLACE_ASSET_ID), x, y));
            addToLayer(MapLayer.CRATE, new Crate(Assets.getAtlasRegions(Constants.TILE_CRATE_ASSET_ID), x, y));
        } else if (layer == MapLayer.SOKOBAN && id == SOKOBAN_ON_PLACE_ID) {
            addToLayer(MapLayer.PLACE, new Place(Assets.getAtlasRegion(Constants.TILE_PLACE_ASSET_ID), x, y));
            addToLayer(MapLayer.SOKOBAN, new Sokoban(x, y));
        }
    }

    private void addToLayer(int layerId, GameObject object) {
        MapLayer layer = getLayer(layerId);
        layer.addObject(object);
    }

}
