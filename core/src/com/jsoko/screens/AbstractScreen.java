package com.jsoko.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jsoko.Constants;
import com.jsoko.controllers.EventController;
import com.jsoko.controllers.ScreenController;
import com.jsoko.events.Event;
import com.jsoko.interfaces.Game;
import com.jsoko.interfaces.Screen;
import com.jsoko.interfaces.ScreenTransition;
import com.jsoko.screens.transitions.Transition;

public abstract class AbstractScreen implements Screen, InputProcessor {

    public final Game game;

    protected final EventController eventController;
    protected final ScreenController screenController;
    protected final OrthographicCamera camera;
    protected final Viewport viewport;
    protected ScreenTransition transition = Transition.SlideDown;

    protected AbstractScreen(Game game) {
        this.game = game;
        this.camera = new OrthographicCamera(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        this.viewport = new ExtendViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
        this.eventController = game.getEventController();
        this.screenController = game.getScreenController();
    }

    public ScreenTransition getTransition() {
        return transition;
    }

    public float getViewportWidth() {
        return camera.viewportWidth;
    }

    public float getViewportHeight() {
        return camera.viewportHeight;
    }

    @Override
    public void create() {
    }

    @Override
    public void resize(int width, int height) {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(this);
        viewport.update(width, height);
        viewport.apply();
        camera.update();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g,
                            Constants.BACKGROUND_COLOR.b, Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        viewport.apply(true);
        batch.setProjectionMatrix(camera.combined);
    }


    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int key) {
        switch (key) {
            case Input.Keys.BACK:   // android back button
            case Input.Keys.ESCAPE:
                eventController.publishEvent(Event.GAME_BACK);
                return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void animate() {

    }
}
