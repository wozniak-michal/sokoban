package com.jsoko.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jsoko.objects.GameObject;
import com.jsoko.objects.GameObjects;

public class Floor extends GameObject {

    public Floor(TextureRegion texture, float x, float y) {
        super(texture, x, y);
        this.type = GameObjects.FLOOR;
    }

    @Override
    public Vector2 getPosition() {
        // always return invalid position
        // we draw it at correct position
        // but we don't want to check it in collisions
        return new Vector2(-9999, -9999);
    }
}
