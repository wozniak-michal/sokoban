package com.jsoko.ui.components;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jsoko.Assets;
import com.jsoko.Constants;
import com.jsoko.events.Event;
import com.jsoko.events.EventHandler;
import com.jsoko.events.data.EventData;
import com.jsoko.interfaces.Game;
import com.jsoko.ui.SokoDialog;

public class HudComponent extends StageComponent implements EventHandler {

    private SokoDialog exitDialog;
    private SokoDialog restartDialog;
    private SokoDialog solverDialog;

    private ImageButton undoButton;
    private TextButton skipLabel;

    public HudComponent(final Game game) {
        super(game);
    }

    @Override
    public void create() {
        super.create();

        createLayout();
        createExitDialog();
        createRestartDialog();
        createSolverDialog();

        eventController.registerForEvents(this);
    }

    private void createLayout() {
        Group topLeftGroup = createTopLeftGroup();
        Group topRightGroup = createTopRightGroup();
        Group bottomLeftGroup = createBottomLeft();
        Group bottomRightGroup = createBottomRight();

        skipLabel = new TextButton(Assets.getString(Constants.GAME_SOLVER_SKIP_ID), skin);
        skipLabel.setVisible(false);
        skipLabel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skipLabel.setVisible(false);
                eventController.publishEvent(Event.GAME_SKIP_SOLVE);
            }
        });

        content.pad(Constants.DEFAULT_BORDER);
        content.add(topLeftGroup).expand().left().top();
        content.add(skipLabel).expand().center().top();
        content.add(topRightGroup).expand().right().top();
        content.row();

        // control keys
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            content.add(bottomLeftGroup).expand().left().bottom();
            content.add(bottomRightGroup).colspan(2).expand().right().bottom();
        }
    }

    private Group createTopRightGroup() {
        ImageButton restartButton = new ImageButton(skin, Constants.BUTTON_RESTART_ID);
        restartButton.setColor(0, 0, 0, Constants.BACK_BUTTON_OPACITY);
        restartButton.setPosition(-restartButton.getWidth(), -restartButton.getHeight());

        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventController.publishEvent(Event.GAME_RESTART_DIALOG_REQUEST);
            }
        });

        undoButton = new ImageButton(skin, Constants.BUTTON_UNDO_ID);
        undoButton.setColor(0, 0, 0, Constants.BACK_BUTTON_OPACITY);
        undoButton.setPosition(-undoButton.getWidth(),
                               -undoButton.getHeight()*2 - Constants.DEFAULT_BORDER);
        undoButton.setDisabled(true);

        undoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventController.publishEvent(Event.GAME_MOVE_UNDO);
            }
        });

        Group group = new Group();
        group.addActor(restartButton);
        group.addActor(undoButton);
        return group;
    }

    private Group createTopLeftGroup() {
        ImageButton exitButton = new ImageButton(skin, Constants.BUTTON_EXIT_ID);
        exitButton.setColor(0, 0, 0, Constants.BACK_BUTTON_OPACITY);
        exitButton.setPosition(0, -exitButton.getHeight());

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventController.publishEvent(Event.GAME_EXIT_DIALOG_REQUEST);
            }
        });

        ImageButton solveButton = new ImageButton(skin, Constants.BUTTON_SOLVER_ID);
        solveButton.setPosition(Constants.DEFAULT_BORDER + solveButton.getWidth(),
                                -solveButton.getHeight());

        solveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventController.publishEvent(Event.GAME_SOLVE_DIALOG_REQUEST);
            }
        });

        Group group = new Group();
        group.addActor(exitButton);
        group.addActor(solveButton);
        return group;
    }

    private Group createBottomLeft() {
        ImageButton leftButton = new ImageButton(skin, Constants.BUTTON_LEFT_ID);
        leftButton.setPosition(0, 0);
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventController.publishEvent(Event.GAME_MOVE_LEFT);
            }
        });

        ImageButton rightButton = new ImageButton(skin, Constants.BUTTON_RIGHT_ID);
        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventController.publishEvent(Event.GAME_MOVE_RIGHT);
            }
        });
        rightButton.setPosition(leftButton.getX() + leftButton.getWidth() + Constants.DEFAULT_BORDER, 0);

        Group group = new Group();
        group.addActor(leftButton);
        group.addActor(rightButton);
        return group;
    }

    private Group createBottomRight() {
        ImageButton downButton = new ImageButton(skin, Constants.BUTTON_DOWN_ID);
        downButton.setPosition(-downButton.getWidth(), 0);

        downButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventController.publishEvent(Event.GAME_MOVE_DOWN);
            }
        });

        ImageButton upButton = new ImageButton(skin, Constants.BUTTON_UP_ID);
        upButton.setPosition(-upButton.getWidth(),
                             downButton.getY() + downButton.getHeight() + Constants.DEFAULT_BORDER);

        upButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventController.publishEvent(Event.GAME_MOVE_UP);
            }
        });

        Group group = new Group();
        group.addActor(upButton);
        group.addActor(downButton);
        return group;
    }

    private void createExitDialog() {
        final String message = Assets.getString(Constants.DIALOG_EXIT_TEXT_ID);

        exitDialog = new SokoDialog(skin)
                .addMessage(message)
                .addNegativeButton(Constants.BUTTON_REJECT_ID)
                .addPositiveButton(Constants.BUTTON_CONFIRM_ID)
                .setResultCallback(new SokoDialog.SokoDialogResult() {
                    @Override
                    public void positive() {
                        eventController.publishEvent(Event.GAME_BACK);
                    }

                    @Override
                    public void negative() {
                    }
                })
                .build(stage);
    }

    private void createRestartDialog() {
        final String message = Assets.getString(Constants.DIALOG_RESTART_TEXT_ID);

        restartDialog = new SokoDialog(skin)
                .addMessage(message)
                .addNegativeButton(Constants.BUTTON_REJECT_ID)
                .addPositiveButton(Constants.BUTTON_RESTART_ID)
                .setResultCallback(new SokoDialog.SokoDialogResult() {
                    @Override
                    public void positive() {
                        eventController.publishEvent(Event.GAME_RESTART_LEVEL);
                    }

                    @Override
                    public void negative() {
                    }
                })
                .build(stage);
    }

    private void createSolverDialog() {
        final String message = Assets.getString(Constants.DIALOG_SOLVER_TEXT_ID);

        solverDialog = new SokoDialog(skin)
                .addMessage(message)
                .addNegativeButton(Constants.BUTTON_REJECT_ID)
                .addPositiveButton(Constants.BUTTON_SOLVER_ID)
                .setResultCallback(new SokoDialog.SokoDialogResult() {
                    @Override
                    public void positive() {
                        eventController.publishEvent(Event.GAME_SOLVE);
                    }

                    @Override
                    public void negative() {
                    }
                })
                .build(stage);
    }

    @Override
    public void onEventReceived(Event event) {
        switch (event) {
            case GAME_LEVEL_COMPLETED:
                skipLabel.setVisible(false);
                break;

            case GAME_SOLVE:
                skipLabel.setVisible(true);
                break;

            case GAME_EXIT_DIALOG_SHOW:
                exitDialog.show();
                break;

            case GAME_RESTART_DIALOG_SHOW:
                restartDialog.show();
                break;

            case GAME_SOLVE_DIALOG_SHOW:
                solverDialog.show();
                break;

            case GAME_OUT_OF_UNDOS:
                undoButton.setDisabled(true);
                break;

            case GAME_UNDOS_AVAILABLE:
                undoButton.setDisabled(false);
                break;
        }
    }

    @Override
    public void onEventWithDataReceived(Event event, EventData eventData) {

    }
}
