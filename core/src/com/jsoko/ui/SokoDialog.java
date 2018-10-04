package com.jsoko.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SokoDialog extends Dialog {

    private final Timer timer = new Timer();

    private final int POSITIVE_RESULT = 1;
    private final int NEGATIVE_RESULT = -1;

    private float ANIMATION_HIDE_TIME = 0.5f;

    private Stage stage;
    private final Skin skin;
    private final SokoDialog dialog;

    private SokoDialogResult resultCallback;
    private boolean shown;

    public SokoDialog(Skin skin) {
        super("", skin);
        this.skin = skin;
        this.dialog = this;
    }

    public void show() {
        if (!shown && stage != null) {
            show(stage);
        }
    }

    @Override
    protected void result(Object obj) {
        if (obj == null || resultCallback == null) {
            return;
        }

        final int result = (int) obj;

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                switch (result) {
                    case POSITIVE_RESULT:
                        resultCallback.positive();
                        break;
                    case NEGATIVE_RESULT:
                        resultCallback.negative();
                        break;
                }
            }
        }, ANIMATION_HIDE_TIME);
    }

    public SokoDialog addMessage(String text) {
        return addMessage(text, "default", Align.center);
    }

    public SokoDialog addMessage(String text, String styleName) {
        return addMessage(text, styleName, Align.center);
    }

    public SokoDialog addMessage(String text, String styleName, int align) {
        Label titleLabel = new Label(text, skin, styleName);
        titleLabel.setWrap(true);
        titleLabel.setAlignment(align);

        getContentTable().add(titleLabel).width(600).row();

        return this;
    }

    public SokoDialog addPositiveButton(String asset) {
        ImageButton confirmButton = new ImageButton(skin, asset);
        confirmButton.pad(0, 20, 0, 20);

        button(confirmButton, POSITIVE_RESULT);

        return this;
    }

    public SokoDialog addNegativeButton(String asset) {
        ImageButton rejectButton = new ImageButton(skin, asset);
        rejectButton.pad(0, 20, 0, 20);

        button(rejectButton, NEGATIVE_RESULT);

        return this;
    }

    public SokoDialog setResultCallback(SokoDialogResult resultCallback) {
        if (resultCallback == null)
            throw new NullPointerException("SokoDialogResult callback can't be null!");

        this.resultCallback = resultCallback;
        return this;
    }

    public SokoDialog build(Stage stage) {
        Table buttonTable = this.getButtonTable();
        buttonTable.padTop(50);

        this.stage = stage;
        pad(50);
        setModal(true);
        setResizable(false);
        setMovable(false);
        setKeepWithinStage(false);

        addListener(new InputListener() {
            @Override
            public boolean keyDown (InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                    dialog.hide();
                    return true;
                }
                return false;
            }
        });

        return this;
    }

    @Override
    public Dialog show(Stage stage) {
        shown = true;
        show(stage, sequence(
                Actions.moveBy(0, stage.getWidth()),
                Actions.moveBy(0, -stage.getWidth(), 0.8f, Interpolation.fade)
        ));
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2),
                    Math.round((stage.getHeight() - getHeight()) / 2));

        return this;
    }

    @Override
    public void hide() {
        shown = false;
        hide(sequence(
                Actions.moveBy(0, getStage().getHeight() + this.getY(), ANIMATION_HIDE_TIME, Interpolation.fade)
        ));
    }

    public interface SokoDialogResult {
        void positive();
        void negative();
    }
}
