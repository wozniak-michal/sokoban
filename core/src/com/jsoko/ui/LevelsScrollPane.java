package com.jsoko.ui;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.jsoko.Constants;
import com.jsoko.Level;
import com.jsoko.LevelGroup;
import com.jsoko.events.Event;
import com.jsoko.events.EventHandler;
import com.jsoko.events.data.EventData;
import com.jsoko.events.data.GroupEventData;
import com.jsoko.events.data.LevelEventData;
import com.jsoko.interfaces.Game;
import com.jsoko.ui.components.PagedScrollPaneComponent;
import com.jsoko.ui.controls.PagedScrollPane;

public class LevelsScrollPane extends PagedScrollPaneComponent implements EventHandler,
        PagedScrollPane.PageChangedHandler {

    private final int ROWS = 3;
    private final int COLUMNS = 7;
    private final int PAGE_LEVELS = ROWS * COLUMNS;

    public LevelsScrollPane(Game game) {
        super(game);
    }

    @Override
    public void create() {
        super.create();

        pagedPane.setPageChangedHandler(this);
        pagedPane.setScrollingDisabled(false, true);
        pagedPane.setOverscroll(true, false);
        pagedPane.setFlingTime(0.1f);
        pagedPane.setSmoothScrolling(true);

        game.getEventController().registerForEvents(this);
    }

    private void processLevelsData(GroupEventData eventData) {
        final int levelsCount = eventData.getGroup().getLevelsCount();
        final int pages = MathUtils.ceil((float)levelsCount / PAGE_LEVELS);
        final String levelsTitle = eventData.getGroup().getName();

        setTitleLabelText(levelsTitle);
        createLevelButtons(eventData.getGroup());
        insertPagesToPaneNavigation(pages);

        setActivePage(1);
    }

    private void createLevelButtons(LevelGroup group) {

        final int levelsCount = group.getLevelsCount();
        final int pages = MathUtils.ceil((float)levelsCount / PAGE_LEVELS);
        final int passedLevels = group.getPassedLevels();
        final PagedScrollPane pagedPane = getPagedPane();

        int elementId = 1;
        try {
            for (int p = 1; p <= pages; p++) {
                Table page = new Table();

                for (int r = 1; r <= ROWS; r++) {
                    page.row().fillX().expandX();

                    for (int c = 1; c <= COLUMNS; c++) {

                        if (elementId > levelsCount) {
                            pagedPane.addPage(page);
                            throw new Exception("Aborting, no more levels...");
                        }

                        Level level = group.getLevels().get(elementId);
                        int levelId = level.getId();
                        boolean locked = (levelId - 1 > passedLevels);
                        boolean passed = (levelId <= passedLevels);

                        Button levelButton = createLevelButton(level, locked, passed);
                        page.add(levelButton).size(110, 110).pad(15, 0, 15, 0);

                        elementId++;
                    }
                }

                pagedPane.addPage(page);
            }
        } catch (Exception ignored) {
        }
    }

    public Button createLevelButton(Level level, boolean locked, boolean passed) {
        if (locked) {
            return createLockedButton();
        } else if (passed) {
            return createNormalButton(level, Constants.BUTTON_COMPLETED_ID);
        } else {
            return createNormalButton(level, Constants.BUTTON_IN_PROGRESS_ID);
        }
    }

    private Button createNormalButton(Level level, String style) {

        LevelEventData eventData = new LevelEventData();
        eventData.setLevel(level);

        Button button = new Button(skin);
        Image background = new Image(skin.getDrawable(style));

        Label label = new Label(Integer.toString(level.getId()), skin);
        label.setFontScale(1.2f);
        label.setAlignment(Align.center);

        button.setUserObject(eventData);
        button.stack(
                background,
                label
        ).expand().fill().row();

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                LevelEventData eventData = (LevelEventData) actor.getUserObject();
                game.getEventController().publishEventWithData(Event.GAME_LEVEL_SELECTED, eventData);
            }
        });

        return button;
    }

    private Button createLockedButton() {
        Button button = new Button(skin);
        Image image = new Image(skin, "locked");
        image.setAlign(Align.center);
        image.setScaling(Scaling.fit);

        button.stack(image).expand().fill();

        return button;
    }

    @Override
    public void handle(int page) {
        this.setActivePage(page);
    }

    @Override
    public void onEventReceived(Event event) {

    }

    @Override
    public void onEventWithDataReceived(Event event, EventData eventData) {
        switch (event) {
            case GAME_LEVELS_DATA:
                recreate();
                processLevelsData((GroupEventData) eventData);
                break;
        }
    }
}
