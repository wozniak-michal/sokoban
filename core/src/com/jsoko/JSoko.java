package com.jsoko;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jsoko.controllers.EventController;
import com.jsoko.controllers.GameController;
import com.jsoko.controllers.ScreenController;
import com.jsoko.events.Event;
import com.jsoko.interfaces.Game;
import com.jsoko.loaders.LevelsLoader;
import com.jsoko.loaders.SettingsLoader;
import com.jsoko.screens.GroupSelectScreen;
import com.jsoko.screens.LevelSelectScreen;
import com.jsoko.screens.MenuScreen;
import com.jsoko.screens.PlayScreen;
import com.jsoko.screens.SummaryScreen;

public class JSoko implements Game, ApplicationListener {

    private SpriteBatch batch;

    private ScreenController screenController;
    private AssetManager assetManager;
    private EventController eventController;

    private World world;
    private GameController gameController;

    public JSoko() {
        this.world = new World(this);
        this.assetManager = new AssetManager();
    }

    @Override
    public void create() {
        // must be created here!
        this.batch = new SpriteBatch();

        createLoaders();
        loadAssets();
        createControllers();
        registerScreens();

        printDebug();
    }

    private void createLoaders() {
        assetManager.setLoader(Settings.class, new SettingsLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(Levels.class, new LevelsLoader(new InternalFileHandleResolver()));
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        screenController.onUpdate(delta);
        screenController.onRender(batch);

    }

    @Override
    public void resize(int width, int height) {
        screenController.onResize(width, height);
    }

    @Override
    public void pause() {
        screenController.onPause();
        eventController.publishEvent(Event.GAME_SAVE_PROGRESS);
    }

    @Override
    public void resume() {
        screenController.onResume();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        eventController.dispose();
        screenController.dispose();
        world.dispose();
        batch.dispose();
        gameController.dispose();
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public GameController getGameController() {
        return gameController;
    }

    public ScreenController getScreenController() {
        return screenController;
    }

    public EventController getEventController() {
        return eventController;
    }

    private void createControllers() {
        screenController = new ScreenController();
        eventController = new EventController();
        gameController = new GameController(this);
    }

    private void loadAssets() {
        Assets.load(assetManager);
    }

    private void registerScreens() {
        screenController.registerState(GameState.SUMMARY, new SummaryScreen(this));
        screenController.registerState(GameState.MENU, new MenuScreen(this));
        screenController.registerState(GameState.GROUP_SELECT, new GroupSelectScreen(this));
        screenController.registerState(GameState.LEVEL_SELECT, new LevelSelectScreen(this));
        screenController.registerState(GameState.GAME, new PlayScreen(this));
        screenController.setState(GameState.MENU, new MenuScreen(this));
    }

    private void printDebug() {
        System.out.println(String.format("# Application Local Storage: %s",
                                         Gdx.files.getLocalStoragePath()));
        System.out.println(String.format("# Application External Storage: %s",
                                         Gdx.files.getExternalStoragePath()));
        System.out.println(String.format("# Memory usage: %.2f MB",
                                         (Gdx.app.getJavaHeap() / 1024.0f / 1024.0f)));
    }
}
