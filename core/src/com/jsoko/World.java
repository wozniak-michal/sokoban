package com.jsoko;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.jsoko.interfaces.Game;
import com.jsoko.interfaces.Updatable;
import com.jsoko.objects.GameObject;
import com.jsoko.objects.gameobjects.Crate;
import com.jsoko.objects.gameobjects.NullObject;
import com.jsoko.objects.gameobjects.Place;
import com.jsoko.objects.gameobjects.Sokoban;

public class World implements Updatable, Disposable {

    private final Game game;
    private final Map map;

    public World(Game game) {
        this.game = game;
        this.map = new Map();
    }

    public int getWidth() {
        return map.getMapWidth();
    }

    public int getHeight() {
        return map.getMapHeight();
    }

    public Sokoban getPlayer() {
        return (Sokoban)map.getLayer(MapLayer.SOKOBAN).getObjects().get(0);
    }

    @Override
    public void update(float delta) {
        for (MapLayer layer : map.getMapLayers()) {
            layer.updateObjects(delta);
        }
    }

    public void render(SpriteBatch batch, float scale) {
        for (MapLayer layer : map.getMapLayers()) {
            layer.renderObjects(batch, scale);
        }
    }

    @Override
    public void dispose() {
        disposeGameObjects();
    }

    private void disposeGameObjects() {
        for (MapLayer layer : map.getMapLayers()) {
            layer.disposeObjects();
        }
    }

    public void loadLevel(final Level level) {
        System.out.println("Loading level: " + level.toString());

        disposeGameObjects();
        map.createFromLevel(level);
        invalidateCrates();
    }

    private void invalidateCrates() {
        MapLayer placeLayer = map.getLayer(MapLayer.PLACE);

        for (GameObject object : placeLayer.getObjects()) {

            Place place = (Place)object;
            Crate crateOnPlace = getObjectTypeAt(Crate.class, place.getPosition());
            if (crateOnPlace != null) {
                crateOnPlace.bindPlace(place);
            }

        }
    }

    public boolean areCratesOnPlaces() {
        MapLayer placeLayer = map.getLayer(MapLayer.PLACE);

        for (GameObject object : placeLayer.getObjects()) {
            Place place = (Place)object;
            if (!place.haveCrate()) {
                return false;
            }
        }
        return true;
    }

    public GameObject getObjectAt(Vector2 targetPosition) {
        for (MapLayer layer : map.getMapLayers()) {
            for (GameObject object : layer.getObjects()) {

                Vector2 position = object.getPosition();
                if (position.epsilonEquals(targetPosition, 1.0f)) {
                    return object;
                }

            }
        }

        return NullObject.instance;
    }

    public <T extends GameObject> T getObjectTypeAt(Class<T> type, Vector2 targetPosition) {
        for (MapLayer layer : map.getMapLayers()) {
            for (GameObject object : layer.getObjects()) {

                Vector2 position = object.getPosition();
                if (position.epsilonEquals(targetPosition, 1.0f) &&
                        type.isAssignableFrom(object.getClass())) {
                    return type.cast(object);
                }

            }
        }
        return null;
    }
}