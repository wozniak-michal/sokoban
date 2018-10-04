package com.jsoko.ui.components;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jsoko.Assets;
import com.jsoko.Constants;
import com.jsoko.GameState;
import com.jsoko.interfaces.Game;
import com.jsoko.ui.SokoDialog;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MenuComponent extends StageComponent {

    private SokoDialog creditsDialog;

    private Image sokobanLogo;
    private TextButton startButton;
    private ImageButton facebookButton;
    private ImageButton infoButton;

    public MenuComponent(Game game) {
        super(game);
    }

    @Override
    public void create() {
        super.create();

        content.pad(Constants.DEFAULT_BORDER);

        createLogo();
        createStartButton();
        createLeftGroup();
        createRightGroup();
        createCreditsDialog();
    }

    private void createLogo() {
        sokobanLogo = new Image(new TextureRegionDrawable(Assets.getAtlasRegion(Constants.LOGO_SOKOBAN)));
        content.add(sokobanLogo).colspan(2).expand().row();
    }

    private void createStartButton() {
        startButton = new TextButton(Assets.getString(Constants.MENU_TAP_TEXT_ID), skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenController.changeState(GameState.GROUP_SELECT);
            }
        });
        content.add(startButton).colspan(2).expand().row();
    }

    private void createLeftGroup() {
        Group leftGroup = new Group();

        facebookButton = new ImageButton(skin, Constants.BUTTON_FACEBOOK_ID);
        leftGroup.addActor(facebookButton);

        content.add(leftGroup).expand().bottom().left();
    }

    private void createRightGroup() {
        Group rightGroup = new Group();

        infoButton = new ImageButton(skin, Constants.BUTTON_INFO_ID);
        infoButton.setPosition(-infoButton.getWidth(), 0);
        infoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                creditsDialog.show();
            }
        });
        rightGroup.addActor(infoButton);

        content.add(rightGroup).expand().bottom().right();

    }

    private void createCreditsDialog() {
        creditsDialog = new SokoDialog(skin)
                .addMessage(Assets.getString(Constants.GAME_CREDITS_TEXT_ID), Constants.LABEL_SMALL_ID)
                .addPositiveButton(Constants.BUTTON_CONFIRM_ID)
                .build(stage);
    }

    public void animate() {
        sokobanLogo.addAction(sequence(
                fadeOut(0),
                moveBy(0, 500),
                delay(0.5f),
                parallel(
                        fadeIn(1.0f, Interpolation.exp5),
                        moveBy(0, -500, 1.0f, Interpolation.exp5)
                )
        ));

        facebookButton.addAction(sequence(
                moveBy(-300, 0),
                delay(0.5f),
                moveBy(300, 0, 1.0f, Interpolation.exp5)
        ));

        infoButton.addAction(sequence(
                moveBy(300, 0),
                delay(0.5f),
                moveBy(-300, 0, 1.0f, Interpolation.exp5)
        ));

        startButton.clearActions();
        startButton.addAction(sequence(
                fadeOut(0),
                delay(2),
                parallel(
                        fadeIn(1),
                        forever(sequence(
                                moveBy(0, 30, 0.5f, Interpolation.pow2),
                                moveBy(0, -30, 0.5f, Interpolation.pow2)
                        ))
                )
        ));
    }
}