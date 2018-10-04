package com.jsoko.ui.components;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.jsoko.Assets;
import com.jsoko.Constants;
import com.jsoko.interfaces.Game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SummaryComponent extends StageComponent {

    private Label completedText;
    private Label tapText;

    public SummaryComponent(Game game) {
        super(game);

        completedText = new Label(Assets.getString(Constants.SUMMARY_COMPLETED_TEXT_ID),
                                  skin,
                                  Constants.LABEL_LARGE_ID);
        completedText.setAlignment(Align.center);

        tapText = new Label(Assets.getString(Constants.SUMMARY_TAP_TEXT_ID),
                            skin,
                            Constants.LABEL_MEDIUM_ID);
        tapText.setAlignment(Align.center);

        content.pad(Constants.DEFAULT_BORDER);
        content.add(completedText).top().padBottom(100).row();
        content.add(tapText).top().row();
    }

    public void animate() {
        completedText.addAction(sequence(
                fadeOut(0),
                moveBy(0, 500),
                parallel(
                    moveBy(0, -500, 1.5f, Interpolation.pow5),
                    fadeIn(1.5f)
                )
        ));

        tapText.clearActions();
        tapText.addAction(sequence(
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
