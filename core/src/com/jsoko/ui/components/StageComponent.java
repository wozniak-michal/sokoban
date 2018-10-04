package com.jsoko.ui.components;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jsoko.Assets;
import com.jsoko.Constants;
import com.jsoko.controllers.EventController;
import com.jsoko.controllers.ScreenController;
import com.jsoko.interfaces.Game;
import com.jsoko.interfaces.Renderable;
import com.jsoko.interfaces.Updatable;

public abstract class StageComponent implements Updatable, Renderable, Disposable {

    public final Game game;

    protected final EventController eventController;
    protected final ScreenController screenController;

    protected final Skin skin;
    protected final Stage stage;
    protected final Table content;

    private static SpriteBatch batch;
    private static boolean batchDisposed;

    public StageComponent(Game game) {
        if (batch == null) {
            batch = new SpriteBatch();
        }
        this.game = game;
        this.stage = new Stage(new ExtendViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT), batch);
        this.skin = Assets.getSkinFile();
        this.content = new Table();
        this.eventController = game.getEventController();
        this.screenController = game.getScreenController();
    }

    public InputProcessor getInputProcessor() {
        return stage;
    }

    public void create() {
        content.setFillParent(true);
        stage.addActor(content);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        stage.draw();
    }

    @Override
    public void dispose() {
        content.clear();
        stage.dispose();
        skin.dispose();

        if (!batchDisposed) {
            batch.dispose();
            batch = null;
            batchDisposed = true;
        }
    }
}