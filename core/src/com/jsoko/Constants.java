package com.jsoko;

import com.badlogic.gdx.graphics.Color;

public class Constants {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 450;

    public static final int VIRTUAL_WIDTH = 1280;
    public static final int VIRTUAL_HEIGHT = 720;

    public static final Color BACKGROUND_COLOR = new Color(0x404040FF);

    public static final int TILE_SIZE = 128;
    public static final float ANIMATION_TIME = 0.2f;
    public static final float MOVING_SPEED = 0.2f;
    public static final float SOLVER_SPEED = 0.3f;

    public static final float BACK_BUTTON_OPACITY = 0.6f;
    public static final float DEFAULT_BORDER = 20.0f;

    public static final String MENU_TAP_TEXT_ID = "menu_tap";

    public static final String GROUP_SELECT_TITLE_ID = "groups_title";

    public static final String SUMMARY_COMPLETED_TEXT_ID = "summary_completed";
    public static final String SUMMARY_TAP_TEXT_ID = "summary_tap";

    public static final String GAME_SOLVER_SKIP_ID = "solver_skip";
    public static final String GAME_CREDITS_TEXT_ID = "credits";

    public static final String DIALOG_EXIT_TEXT_ID = "dialog_exit_text";
    public static final String DIALOG_RESTART_TEXT_ID = "dialog_restart_text";
    public static final String DIALOG_SOLVER_TEXT_ID = "dialog_solver_text";

    public static final String BUTTON_UNDO_ID = "undo";
    public static final String BUTTON_CONFIRM_ID = "confirm";
    public static final String BUTTON_REJECT_ID = "reject";
    public static final String BUTTON_RESTART_ID = "restart";
    public static final String BUTTON_FACEBOOK_ID = "facebook";
    public static final String BUTTON_INFO_ID = "info_gold";
    public static final String BUTTON_SOLVER_ID = "solve";
    public static final String BUTTON_EXIT_ID = "exit";
    public static final String BUTTON_UP_ID = "arrow_up";
    public static final String BUTTON_DOWN_ID = "arrow_down";
    public static final String BUTTON_LEFT_ID = "arrow_left";
    public static final String BUTTON_RIGHT_ID = "arrow_right";

    public static final String BUTTON_IN_PROGRESS_ID = "round_gray";
    public static final String BUTTON_COMPLETED_ID = "round_green";
    public static final String BUTTON_LOCKED_ID = "locked";

    public static final String LABEL_SMALL_ID = "label_small";
    public static final String LABEL_MEDIUM_ID = "label_medium";
    public static final String LABEL_LARGE_ID = "label_large";

    public static final String LOGO_SOKOBAN = "sokoban_logo";

    public static final String SOKOBAN_WALK_UP_ANIMATION_ID = "SokobanWalkUp";
    public static final String SOKOBAN_WALK_DOWN_ANIMATION_ID = "SokobanWalkDown";
    public static final String SOKOBAN_WALK_LEFT_ANIMATION_ID = "SokobanWalkLeft";
    public static final String SOKOBAN_WALK_RIGHT_ANIMATION_ID = "SokobanWalkRight";

    public static final String TILE_FLOOR_ASSET_ID = "FloorBrick";
    public static final String TILE_WALL_ASSET_ID = "WallBrick";
    public static final String TILE_PLACE_ASSET_ID = "Place3";
    public static final String TILE_CRATE_ASSET_ID = "CrateWood";

    public static final String GRASS_BACKGROUND_FILE = "backgrounds/grass.jpg";
    public static final String MENU_BACKGROUND_FILE = "backgrounds/menu.jpg";
    public static final String SKIN_FILE = "skin/skin.json";
    public static final String STRINGS_BUNDLES_PATH = "i18n/strings";
    public static final String SETTINGS_FILE = "settings.json";
    public static final String LEVELS_FILE = "levels/levels.data.json";
    public static final String LEVELS_PROGRESS_FILE = "levels.user.data.json";
}
