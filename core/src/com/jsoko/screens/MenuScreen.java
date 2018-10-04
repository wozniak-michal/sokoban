package com.jsoko.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jsoko.Assets;
import com.jsoko.GameState;
import com.jsoko.interfaces.Game;
import com.jsoko.screens.transitions.Transition;
import com.jsoko.ui.components.MenuComponent;

public class MenuScreen extends AbstractScreen {

    private MenuComponent menu;

    public MenuScreen(Game game) {
        super(game);
        this.menu = new MenuComponent(game);
        this.transition = Transition.SlideDown;
    }

    @Override
    public void create() {
        menu.create();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(menu.getInputProcessor());
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        menu.resize(width, height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        menu.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.getMenuBackground(), 0, 0, getViewportWidth(), getViewportHeight());
        batch.end();
        batch.enableBlending();
        menu.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        menu.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenController.changeState(GameState.GROUP_SELECT);
        return true;
    }

    @Override
    public void animate() {
        menu.animate();
    }

    @Override
    public boolean keyDown(int key) {
        switch (key) {
            case Input.Keys.BACK:
                Gdx.app.exit();
                break;

            default:
                return false;
        }
        return true;
    }
}
