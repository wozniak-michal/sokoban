package com.jsoko.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jsoko.Assets;
import com.jsoko.events.Event;
import com.jsoko.interfaces.Game;
import com.jsoko.screens.transitions.Transition;
import com.jsoko.ui.LevelsScrollPane;

public class LevelSelectScreen extends AbstractScreen {

    private LevelsScrollPane levelsScrollPane;

    public LevelSelectScreen(Game game) {
        super(game);
        this.levelsScrollPane = new LevelsScrollPane(game);
        this.transition = Transition.SlideDown;
    }

    @Override
    public void create() {
        super.create();
        levelsScrollPane.create();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(levelsScrollPane.getInputProcessor());
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        levelsScrollPane.resize(width, height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        levelsScrollPane.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.getMenuBackground(), 0, 0, getViewportWidth(), getViewportHeight());
        batch.end();
        batch.enableBlending();
        levelsScrollPane.render(batch);
    }

    @Override
    public void show() {
        super.show();
        eventController.publishEvent(Event.GAME_LEVELS_SCREEN_ENTERED);
    }
}
