package com.jsoko.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jsoko.objects.GameObject;
import com.jsoko.objects.GameObjects;

public class Wall extends GameObject {

    public Wall(TextureRegion texture, float x, float y) {
        super(texture, x, y);
        this.type = GameObjects.WALL;
    }
}
