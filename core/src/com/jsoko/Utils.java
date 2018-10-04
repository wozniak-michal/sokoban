package com.jsoko;

import com.badlogic.gdx.utils.Json;
import com.jsoko.events.Event;

public class Utils {

    public static Json Json = new Json();

    public static Event convertMoveToMoveEvent(Move move) {
        switch (move) {
            case UP:
                return Event.GAME_MOVE_UP;
            case DOWN:
                return Event.GAME_MOVE_DOWN;
            case LEFT:
                return Event.GAME_MOVE_LEFT;
            case RIGHT:
                return Event.GAME_MOVE_RIGHT;
            default:
                throw new IllegalArgumentException("Invalid move!");
        }
    }

    public static Move convertMoveEventToMove(final Event event) {
        switch (event) {
            case GAME_MOVE_UP:
            case SOLVER_MOVE_UP:
                return Move.UP;

            case GAME_MOVE_DOWN:
            case SOLVER_MOVE_DOWN:
                return Move.DOWN;

            case GAME_MOVE_LEFT:
            case SOLVER_MOVE_LEFT:
                return Move.LEFT;

            case GAME_MOVE_RIGHT:
            case SOLVER_MOVE_RIGHT:
                return Move.RIGHT;

            default:
                throw new IllegalArgumentException("Invalid move event!");
        }
    }
}
