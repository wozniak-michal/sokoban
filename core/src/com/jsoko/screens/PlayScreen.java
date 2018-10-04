package com.jsoko.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jsoko.Assets;
import com.jsoko.World;
import com.jsoko.WorldRenderer;
import com.jsoko.events.Event;
import com.jsoko.interfaces.Game;
import com.jsoko.screens.transitions.Transition;
import com.jsoko.ui.components.HudComponent;

public class PlayScreen extends AbstractScreen {

    private final World world;
    private final WorldRenderer worldRenderer;
    private final HudComponent hud;

    public PlayScreen(Game game) {
        super(game);

        this.world = game.getWorld();
        this.worldRenderer = new WorldRenderer();
        this.hud = new HudComponent(game);
        this.transition = Transition.SlideUp;
    }

    @Override
    public void create() {
        super.create();
        hud.create();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hud.getInputProcessor());
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        worldRenderer.resize(width, height);
        hud.resize(width, height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        world.update(delta);
        hud.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.getGameBackground(), 0, 0, getViewportWidth(), getViewportHeight());
        batch.end();
        worldRenderer.render(world, batch);
        hud.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        hud.dispose();
        world.dispose();
    }

    @Override
    public boolean keyDown(int key) {
        switch (key) {
            case Input.Keys.LEFT:
                eventController.publishEvent(Event.GAME_MOVE_LEFT);
                return true;

            case Input.Keys.RIGHT:
                eventController.publishEvent(Event.GAME_MOVE_RIGHT);
                return true;

            case Input.Keys.UP:
                eventController.publishEvent(Event.GAME_MOVE_UP);
                return true;

            case Input.Keys.DOWN:
                eventController.publishEvent(Event.GAME_MOVE_DOWN);
                return true;

            case Input.Keys.BACKSPACE:
                eventController.publishEvent(Event.GAME_MOVE_UNDO);
                return true;

            case Input.Keys.BACK:   // android back button
            case Input.Keys.ESCAPE:
                eventController.publishEvent(Event.GAME_EXIT_DIALOG_REQUEST);
                return true;

        }

        return false;
    }

}
