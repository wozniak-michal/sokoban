package com.jsoko.interfaces;

import com.jsoko.World;
import com.jsoko.controllers.EventController;
import com.jsoko.controllers.GameController;
import com.jsoko.controllers.ScreenController;

public interface Game {
    ScreenController getScreenController();

    EventController getEventController();

    GameController getGameController();

    World getWorld();
}
