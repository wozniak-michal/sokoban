package com.jsoko.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jsoko.objects.GameObject;
import com.jsoko.objects.GameObjects;

public class Place extends GameObject {

    private com.jsoko.objects.gameobjects.Crate crate;

    public Place(TextureRegion texture, float x, float y) {
        super(texture, x, y);
        this.type = GameObjects.PLACE;
    }

    @Override
    public void dispose() {

    }

    public void bindCrate(com.jsoko.objects.gameobjects.Crate crate) {
        this.crate = crate;
    }

    public boolean haveCrate() {
        return crate != null;
    }

    public com.jsoko.objects.gameobjects.Crate getCrate() {
        return crate;
    }

}
