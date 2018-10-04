package com.jsoko.ui;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.jsoko.Assets;
import com.jsoko.Constants;
import com.jsoko.LevelGroup;
import com.jsoko.events.Event;
import com.jsoko.events.EventHandler;
import com.jsoko.events.data.EventData;
import com.jsoko.events.data.GroupEventData;
import com.jsoko.events.data.GroupsEventData;
import com.jsoko.interfaces.Game;
import com.jsoko.ui.components.PagedScrollPaneComponent;
import com.jsoko.ui.controls.PagedScrollPane;

public class GroupsScrollPane extends PagedScrollPaneComponent implements EventHandler,
        PagedScrollPane.PageChangedHandler {

    private final int PER_ROW = 3;
    private final int COLUMNS = 3;

    private Table groups;

    public GroupsScrollPane(Game game) {
        super(game);
        this.groups = new Table();
    }

    @Override
    public void create() {
        super.create();

        setTitleLabelText(Assets.getString(Constants.GROUP_SELECT_TITLE_ID));

        groups.setFillParent(true);

        pagedPane.setPageChangedHandler(this);
        pagedPane.setScrollingDisabled(true, false);
        pagedPane.setScrollBarPositions(true, true);
        pagedPane.setOverscroll(false, true);
        pagedPane.setFadeScrollBars(false);
        pagedPane.setFlingTime(0.1f);
        pagedPane.setSmoothScrolling(true);
        pagedPane.setVariableSizeKnobs(true);

        game.getEventController().registerForEvents(this);
    }

    private void processGroupData(GroupsEventData eventData) {

        Array<LevelGroup> groupData = eventData.getGroups();

        final int count = groupData.size - 1;
        final int rows = MathUtils.ceil((float)count / PER_ROW);

        try {
            int elemsCreated = 1;

            groups.clearChildren();

            for (int r = 1; r <= rows; r++) {
                for (int c = 1; c <= COLUMNS; c++) {

                    if (elemsCreated > count) {
                        groups.row();
                        throw new Exception("End of elements...");
                    }

                    LevelGroup group = groupData.get(elemsCreated);

                    boolean locked = false;
                    boolean passed = (group.getPassedLevels() >= group.getLevelsCount());
                    Button button = createGroupButton(group, locked, passed);

                    groups.add(button).size(350, 125).pad(15);
                    elemsCreated++;
                }

                groups.row();
            }
        } catch (Exception ignored) {
        }

        pagedPane.addPage(groups);
    }

    public Button createGroupButton(LevelGroup group, boolean locked, boolean passed) {
        if (locked) {
            return createLockedButton();
        } else if (passed) {
            return createNormalButton(group, Constants.BUTTON_COMPLETED_ID);
        } else {
            return createNormalButton(group, Constants.BUTTON_IN_PROGRESS_ID);
        }
    }

    private Button createNormalButton(LevelGroup group, String style) {

        GroupEventData eventData = new GroupEventData();
        eventData.setGroup(group);

        Button button = new Button(skin);

        Image background = new Image(skin.getDrawable(style));
        background.setScaling(Scaling.stretch);

        Label label = new Label(group.getName(), skin);
        label.setAlignment(Align.center);
        label.setEllipsis(true);

        Label progress = new Label("", skin, Constants.LABEL_SMALL_ID);
        progress.setText(String.format("%d / %d", group.getPassedLevels(), group.getLevelsCount()));
        progress.setAlignment(Align.center);

        button.setUserObject(eventData);
        button.stack(
                background,
                label
        ).expand().fill().row();

        button.add(progress);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GroupEventData eventData = (GroupEventData) actor.getUserObject();
                game.getEventController().publishEventWithData(Event.GAME_GROUP_SELECTED, eventData);
            }
        });

        return button;
    }

    private Button createLockedButton() {
        Button button = new Button(skin);
        Image image = new Image(skin, Constants.BUTTON_LOCKED_ID);
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
            case GAME_GROUPS_DATA:
                recreate();
                processGroupData((GroupsEventData) eventData);
                break;
        }
    }
}
