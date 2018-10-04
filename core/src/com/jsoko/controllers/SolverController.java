package com.jsoko.controllers;


import com.badlogic.gdx.utils.Timer;
import com.jsoko.Constants;
import com.jsoko.Level;
import com.jsoko.events.Event;
import com.jsoko.interfaces.Game;

public class SolverController {

    private final Timer timer = new Timer();
    private final EventController eventController;

    private String data;
    private int current;

    public SolverController(Game game) {
        this.eventController = game.getEventController();
    }

    public void solve(Level level) {
        this.current = 0;
        this.data = level.getSolveData().toLowerCase();

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (hasNext()) {
                    Event moveEvent = nextMoveEvent();
                    eventController.publishEvent(moveEvent);
                }
            }
        }, 1, Constants.SOLVER_SPEED, data.length());
    }

    public void stop() {
        timer.clear();
        data = null;
    }

    private boolean hasNext() {
        return current < data.length();
    }

    private Event nextMoveEvent() {
        char nextMove = data.charAt(current);

        current++;

        switch (nextMove) {
            case 'u':
                return Event.SOLVER_MOVE_UP;
            case 'd':
                return Event.SOLVER_MOVE_DOWN;
            case 'l':
                return Event.SOLVER_MOVE_LEFT;
            case 'r':
                return Event.SOLVER_MOVE_RIGHT;
            default:
                throw new IllegalArgumentException("Invalid move character!");
        }
    }

}
