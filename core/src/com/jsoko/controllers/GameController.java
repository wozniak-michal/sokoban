package com.jsoko.controllers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.jsoko.GameState;
import com.jsoko.Level;
import com.jsoko.LevelGroup;
import com.jsoko.Move;
import com.jsoko.Utils;
import com.jsoko.World;
import com.jsoko.events.Event;
import com.jsoko.events.EventHandler;
import com.jsoko.events.data.EventData;
import com.jsoko.events.data.GroupEventData;
import com.jsoko.events.data.GroupsEventData;
import com.jsoko.events.data.LevelEventData;
import com.jsoko.interfaces.Game;

public class GameController implements EventHandler, Disposable {

    private final Timer timer = new Timer();

    private final EventController eventController;
    private final ScreenController screenController;

    private final SolverController solverController;
    private final LevelController levelController;
    private final LogicController logicController;
    private final World world;

    private boolean levelCompletedHandled;
    private boolean undosAvailableHandled;
    private boolean inputDisabled;

    public GameController(Game game) {
        this.eventController = game.getEventController();
        this.screenController = game.getScreenController();

        this.world = game.getWorld();
        this.levelController = new LevelController();
        this.solverController = new SolverController(game);
        this.logicController = new LogicController(game);

        eventController.registerForEvents(this);
    }

    public void saveGame() {
        levelController.saveUserProgress();
    }

    @Override
    public void onEventReceived(Event event) {
        switch (event) {
            case GAME_SAVE_PROGRESS:
                saveGame();
                break;

            case SOLVER_MOVE_UP:
            case SOLVER_MOVE_DOWN:
            case SOLVER_MOVE_LEFT:
            case SOLVER_MOVE_RIGHT:
                processMoveEvent(event);
                break;

            case GAME_MOVE_UP:
            case GAME_MOVE_DOWN:
            case GAME_MOVE_LEFT:
            case GAME_MOVE_RIGHT:
                if (!inputDisabled) {
                    processMoveEvent(event);
                }
                break;

            case GAME_BACK:
                stopGame();
                break;

            case GAME_LEVELS_SCREEN_ENTERED:
                sendLevelsData();
                break;

            case GAME_GROUPS_SCREEN_ENTERED:
                sendLevelGroupsData();
                break;

            case GAME_SOLVE:
                loadLevel();
                solverController.solve(levelController.getCurrentLevel());
                inputDisabled = true;
                break;

            case GAME_SKIP_SOLVE:
                scheduleSummaryScreenWithNextLevel(0);
                break;

            case GAME_RESTART_LEVEL:
                if (!inputDisabled) {
                    loadLevel();
                }
                break;

            case GAME_MOVE_UNDO:
                if (!inputDisabled) {
                    processUndoEvent();
                }
                break;

            case GAME_SOLVE_DIALOG_REQUEST:
                if (!inputDisabled) {
                    eventController.publishEvent(Event.GAME_SOLVE_DIALOG_SHOW);
                }
                break;

            case GAME_RESTART_DIALOG_REQUEST:
                if (!inputDisabled) {
                    eventController.publishEvent(Event.GAME_RESTART_DIALOG_SHOW);
                }
                break;

            case GAME_EXIT_DIALOG_REQUEST:
                eventController.publishEvent(Event.GAME_EXIT_DIALOG_SHOW);
                break;

            case GAME_SUMMARY_SCREEN_EXIT:
                screenController.popScreen();
                break;
        }
    }

    private void stopGame() {
        screenController.popScreen();
        inputDisabled = false;
        solverController.stop();
    }

    private void sendLevelsData() {
        LevelGroup group = levelController.getGroupData();

        GroupEventData groupData = new GroupEventData();
        groupData.setGroup(group);

        eventController.publishEventWithData(Event.GAME_LEVELS_DATA, groupData);
    }

    private void sendLevelGroupsData() {
        Array<LevelGroup> groups = levelController.getGroupsData();

        GroupsEventData groupData = new GroupsEventData();
        groupData.setGroups(groups);

        eventController.publishEventWithData(Event.GAME_GROUPS_DATA, groupData);
    }

    private void processUndoEvent() {
        if (logicController.canUndo()) {
            logicController.undoMove();
        } else {
            eventController.publishEvent(Event.GAME_OUT_OF_UNDOS);
            undosAvailableHandled = false;
        }
    }

    @Override
    public void onEventWithDataReceived(Event event, EventData eventData) {
        switch (event) {
            case GAME_GROUP_SELECTED:
                GroupEventData groupData = (GroupEventData)eventData;
                levelController.setCurrentGroup(groupData.getGroup().getId());

                screenController.changeState(GameState.LEVEL_SELECT);
                break;

            case GAME_LEVEL_SELECTED:
                LevelEventData levelData = (LevelEventData)eventData;
                levelController.setCurrentLevel(levelData.getLevel().getId());

                startGame();
                break;
        }
    }

    private void startGame() {
        loadLevel();
        screenController.changeState(GameState.GAME);
    }

    public void processMoveEvent(Event moveEvent) {
        Move move = Utils.convertMoveEventToMove(moveEvent);

        logicController.process(move);

        if (!undosAvailableHandled && logicController.canUndo()) {
            eventController.publishEvent(Event.GAME_UNDOS_AVAILABLE);
            undosAvailableHandled = true;
        }

        if (!levelCompletedHandled && levelCompleted()) {
            scheduleSummaryScreenWithNextLevel(1);
            eventController.publishEvent(Event.GAME_LEVEL_COMPLETED);
            levelCompletedHandled = true;
        }
    }

    private boolean levelCompleted() {
        return world.areCratesOnPlaces();
    }

    private void scheduleSummaryScreenWithNextLevel(int delay) {
        scheduleSummaryScreen(delay);
        scheduleLoadNextLevel(delay + 1);
    }

    private void scheduleSummaryScreen(int delay) {
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                screenController.changeState(GameState.SUMMARY);
            }
        }, delay);
    }

    private void scheduleLoadNextLevel(int delay) {
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                loadNextLevel();
            }
        }, delay);
    }

    private void loadNextLevel() {
        solverController.stop();

        levelController.nextLevel();
        loadLevel();

        levelCompletedHandled = false;
        inputDisabled = false;
    }

    private void loadLevel() {
        Level level = levelController.getCurrentLevel();
        world.loadLevel(level);
    }

    @Override
    public void dispose() {
        levelController.dispose();
    }
}
