package com.jsoko.interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenTransition {
    float getDuration();

    void render(SpriteBatch batch, Texture previousScreenTexture, Texture nextScreenTexture,
                float alpha);
}
