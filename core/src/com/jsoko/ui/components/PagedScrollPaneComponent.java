package com.jsoko.ui.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.jsoko.Constants;
import com.jsoko.events.Event;
import com.jsoko.interfaces.Game;
import com.jsoko.ui.controls.PagedScrollPane;

public class PagedScrollPaneComponent extends StageComponent {

    public PagedScrollPane pagedPane;
    private HorizontalGroup paneNavigation;
    private Label titleLabel;
    private ImageButton backButton;

    public PagedScrollPaneComponent(Game game) {
        super(game);
    }

    public PagedScrollPane getPagedPane() {
        return pagedPane;
    }

    public void recreate() {
        pagedPane.recreate();
        paneNavigation.clearChildren();
    }

    @Override
    public void create() {
        super.create();

        createTitleLabel();
        createPane();
        createPaneNavigation();
        createButtons();
    }

    protected void insertPagesToPaneNavigation(int pages) {
        for (int i = 1; i <= pages; i++) {
            Label label = new Label(String.valueOf(i), skin);
            paneNavigation.addActor(label);
        }
        paneNavigation.pack();
    }

    protected void setTitleLabelText(String text) {
        titleLabel.setText(text);
        titleLabel.invalidate();
    }

    protected void setActivePage(int page) {
        SnapshotArray<Actor> actors = paneNavigation.getChildren();
        for (int i = 0; i < actors.size; i++) {
            Label label = (Label) actors.get(i);
            label.setColor(0, 0, 0, 0.4f);
            if (i + 1 == page) {
                label.setColor(1, 1, 1, 1);
            }
        }
    }

    protected void createPane() {
        if (pagedPane != null) {
            pagedPane.clear();
            content.removeActor(pagedPane);
        }

        pagedPane = new PagedScrollPane(skin);

        content.add(pagedPane).expandX().center().row();
    }

    protected void createPaneNavigation() {
        if (paneNavigation != null) {
            paneNavigation.clear();
            content.removeActor(paneNavigation);
        }

        paneNavigation = new HorizontalGroup();
        paneNavigation.space(20);

        content.add(paneNavigation).top().row();
    }

    protected void createTitleLabel() {
        if (titleLabel != null) {
            titleLabel.clear();
            content.removeActor(titleLabel);
        }

        titleLabel = new Label("", skin);
        content.add(titleLabel).space(10).pad(10).center().row();
    }

    protected void createButtons() {
        backButton = new ImageButton(skin, Constants.BUTTON_LEFT_ID);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getEventController().publishEvent(Event.GAME_BACK);
            }
        });
        content.add(backButton).expand().pad(Constants.DEFAULT_BORDER).bottom().left().row();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        pagedPane.setWidth(stage.getViewport().getWorldWidth());
    }

    @Override
    public void dispose() {
        super.dispose();
        pagedPane.clear();
        paneNavigation.clear();
    }
}
