package com.jsoko.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jsoko.objects.GameObject;
import com.jsoko.objects.GameObjects;

public class NullObject extends GameObject {

    public static final NullObject instance = new NullObject(null, 0, 0);

    public NullObject(TextureRegion texture, float x, float y) {
        super(texture, x, y);
        this.type = GameObjects.NULL;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
    }

}
