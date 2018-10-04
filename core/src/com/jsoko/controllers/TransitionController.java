package com.jsoko.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.jsoko.interfaces.ScreenTransition;
import com.jsoko.screens.AbstractScreen;

public class TransitionController implements Disposable {

    private final Matrix4 projection = new Matrix4();
    private ScreenTransition screenTransition;
    private AbstractScreen prevScreen;
    private AbstractScreen nextScreen;
    private FrameBuffer curFbo;
    private FrameBuffer nextFbo;
    private float timeElapsed;
    private boolean done;
    private ShapeRenderer shapeRenderer;

    public TransitionController() {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.setAutoShapeType(true);
    }

    public boolean doneTransition() {
        return done;
    }

    public void start(AbstractScreen prevScreen, AbstractScreen nextScreen) {

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        projection.setToOrtho2D(0, 0, width, height);
        curFbo = new FrameBuffer(Format.RGBA8888, width, height, false);
        nextFbo = new FrameBuffer(Format.RGBA8888, width, height, false);

        done = false;
        timeElapsed = 0;
        this.prevScreen = prevScreen;
        this.nextScreen = nextScreen;
        screenTransition = nextScreen.getTransition();

        nextScreen.show();
        nextScreen.resize(width, height);
        nextScreen.update(Gdx.graphics.getDeltaTime());
        nextScreen.animate();

        if (prevScreen != null) {
            prevScreen.pause();
        }
        nextScreen.pause();

        // disable input
        Gdx.input.setInputProcessor(null);
    }

    public void render(SpriteBatch batch) {
        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
        float duration = screenTransition.getDuration();

        timeElapsed = Math.min(timeElapsed + deltaTime, duration);

        if (timeElapsed >= duration) {
            finishTransition();
        } else {
            progressTransition(batch, duration);
        }
    }

    private void finishTransition() {
        if (prevScreen != null) {
            prevScreen.hide();
        }
        nextScreen.resume();
        nextScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        prevScreen = nextScreen;
        nextScreen = null;
        done = true;

        curFbo.dispose();
        nextFbo.dispose();
    }

    private void progressTransition(SpriteBatch batch, float duration) {
        renderScreenToBuffer(batch, prevScreen, curFbo);
        renderScreenToBuffer(batch, nextScreen, nextFbo);

        float alpha = timeElapsed / duration;

        batch.setProjectionMatrix(projection);
        screenTransition.render(
                batch,
                curFbo.getColorBufferTexture(),
                nextFbo.getColorBufferTexture(),
                alpha);
    }

    private void renderScreenToBuffer(SpriteBatch batch, AbstractScreen screen, FrameBuffer buffer) {
        buffer.begin();
        if (screen != null) {
            screen.render(batch);
        } else {
            renderEmpty(buffer);
        }
        buffer.end();
    }

    private void renderEmpty(FrameBuffer buffer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, buffer.getWidth(), buffer.getHeight());
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
