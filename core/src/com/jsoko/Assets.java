package com.jsoko;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

public class Assets {
    private static I18NBundle strings;
    private static Settings settings;
    private static Texture menuBackground;
    private static Texture grassBackground;
    private static Skin skin;
    private static Levels levels;

    public static Texture getMenuBackground() {
        return menuBackground;
    }

    public static Texture getGameBackground() {
        return grassBackground;
    }

    public static Skin getSkinFile() {
        return skin;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static Levels getLevels() {
        return levels;
    }

    public static String getString(String string) {
        return strings.get(string);
    }

    public static TextureRegion getAtlasRegion(String asset) {
        return skin.getRegion(asset);
    }

    public static Array<TextureRegion> getAtlasRegions(String asset) {
        return skin.getRegions(asset);
    }

    public static Animation<TextureRegion> getAnimation(String asset) {
        return new Animation<>(Constants.ANIMATION_TIME, getAtlasRegions(asset), Animation.PlayMode.LOOP);
    }

    public static void load(AssetManager manager) {
        manager.load(Constants.STRINGS_BUNDLES_PATH, I18NBundle.class);
        manager.load(Constants.MENU_BACKGROUND_FILE, Texture.class);
        manager.load(Constants.GRASS_BACKGROUND_FILE, Texture.class);
        manager.load(Constants.SKIN_FILE, Skin.class);
        manager.load(Constants.SETTINGS_FILE, Settings.class);
        manager.load(Constants.LEVELS_FILE, Levels.class);
        manager.load(Constants.LEVELS_PROGRESS_FILE, Levels.class);

        manager.finishLoading();

        strings = manager.get(Constants.STRINGS_BUNDLES_PATH);
        menuBackground = manager.get(Constants.MENU_BACKGROUND_FILE);
        grassBackground = manager.get(Constants.GRASS_BACKGROUND_FILE);
        skin = manager.get(Constants.SKIN_FILE);
        settings = manager.get(Constants.SETTINGS_FILE);
        levels = manager.get(Constants.LEVELS_FILE);
    }

    public static void dispose() {
        strings = null;
        menuBackground = null;
        grassBackground = null;
        skin = null;
        settings = null;
        levels.getGroups().clear();
        levels = null;
    }
}