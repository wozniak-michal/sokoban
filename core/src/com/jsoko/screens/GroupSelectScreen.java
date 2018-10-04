package com.jsoko.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jsoko.Assets;
import com.jsoko.events.Event;
import com.jsoko.interfaces.Game;
import com.jsoko.screens.transitions.Transition;
import com.jsoko.ui.GroupsScrollPane;

public class GroupSelectScreen extends AbstractScreen {

    private GroupsScrollPane groupsScrollPane;

    public GroupSelectScreen(Game game) {
        super(game);
        this.groupsScrollPane = new GroupsScrollPane(game);
        this.transition = Transition.SlideUp;
    }

    @Override
    public void create() {
        super.create();
        groupsScrollPane.create();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(groupsScrollPane.getInputProcessor());
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        groupsScrollPane.resize(width, height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        groupsScrollPane.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.getMenuBackground(), 0, 0, getViewportWidth(), getViewportHeight());
        batch.end();
        batch.enableBlending();
        groupsScrollPane.render(batch);
    }
    @Override
    public void show() {
        super.show();
        eventController.publishEvent(Event.GAME_GROUPS_SCREEN_ENTERED);
    }
}
