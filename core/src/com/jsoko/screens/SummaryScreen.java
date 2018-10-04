package com.jsoko.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jsoko.events.Event;
import com.jsoko.interfaces.Game;
import com.jsoko.screens.transitions.Transition;
import com.jsoko.ui.components.SummaryComponent;

public class SummaryScreen extends AbstractScreen {

    private final SummaryComponent summary;

    public SummaryScreen(Game game) {
        super(game);
        this.summary = new SummaryComponent(game);
        this.transition = Transition.SlideDown;
    }

    @Override
    public void create() {
        super.create();
        summary.create();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        summary.resize(width, height);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(summary.getInputProcessor());
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        summary.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        summary.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        summary.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        eventController.publishEvent(Event.GAME_SUMMARY_SCREEN_EXIT);
        return true;
    }

    @Override
    public void animate() {
        summary.animate();
    }
}
