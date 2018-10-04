package com.jsoko;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldRenderer {

    public static final float SCALE = 1.0f / 128.0f;
    private static final int VIRTUAL_WIDTH = 14;
    private static final int VIRTUAL_HEIGHT = 9;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private boolean cameraShouldMove;

    public WorldRenderer() {
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
        camera.update();
    }

    public void render(World world, SpriteBatch batch) {
        if (world.getWidth() > VIRTUAL_HEIGHT || world.getHeight() > VIRTUAL_HEIGHT) {
            cameraShouldMove = true;
        } else {
            cameraShouldMove = false;
        }

        viewport.apply();

        updateCamera(world);

        batch.enableBlending();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.render(batch, SCALE);
        batch.end();
    }

    private void updateCamera(World world) {
        if (cameraShouldMove) {
            Vector2 playerPosition = world.getPlayer().getPosition();
            Vector3 newCameraPosition = new Vector3(playerPosition.x * SCALE,
                                                    playerPosition.y * SCALE, 0);
            camera.position.interpolate(newCameraPosition, 0.45f, Interpolation.exp10In);
        } else {
            camera.position.set(world.getWidth() * 0.5f, 1 - world.getHeight() * 0.5f, 0);
        }
    }
}
