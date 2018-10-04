package com.jsoko;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jsoko.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class MapLayer {

    public static final int FLOOR   = 0;
    public static final int WALL    = 1;
    public static final int PLACE   = 2;
    public static final int CRATE   = 3;
    public static final int SOKOBAN = 4;

    private final List<GameObject> objects;

    public MapLayer() {
        this.objects = new ArrayList<>();
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public void disposeObjects() {
        for (GameObject object : objects) {
            object.dispose();
        }
        objects.clear();
    }

    public void updateObjects(float delta) {
        for (GameObject object : objects) {
            object.update(delta);
        }
    }

    public void renderObjects(SpriteBatch batch, float scale) {
        for (GameObject object : objects) {
            object.render(batch, scale);
        }
    }
}
