package com.jsoko.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Screen {
    void create();

    void resize(int width, int height);

    void update(float delta);

    void render(SpriteBatch batch);

    void pause();

    void resume();

    void show();

    void hide();

    void dispose();
}
