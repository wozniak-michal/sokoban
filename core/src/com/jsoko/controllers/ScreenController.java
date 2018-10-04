package com.jsoko.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.jsoko.GameState;
import com.jsoko.interfaces.Screen;
import com.jsoko.screens.AbstractScreen;

import java.util.Stack;

public class ScreenController implements Disposable {

    private final TransitionController transitionController;
    private final ObjectMap<GameState, AbstractScreen> registeredStates;
    private final Stack<AbstractScreen> screens;

    public ScreenController() {
        registeredStates = new ObjectMap<>();
        transitionController = new TransitionController();
        screens = new Stack<>();
    }

    public void registerState(GameState state, AbstractScreen screen) {
        if (screen != null) {
            screen.create();
            registeredStates.put(state, screen);
        } else
            throw new NullPointerException("Cannot register null gamescreen!");
    }

    public void setState(GameState state, AbstractScreen newScreen) {

        AbstractScreen oldScreen = null;

        if (screens.size() > 0) {
            oldScreen = screens.peek();
        }

        newScreen.create();
        screens.push(newScreen);

        registeredStates.put(state, newScreen); // cache it

        transitionController.start(oldScreen, newScreen);
    }

    public void changeState(GameState state) {
        AbstractScreen oldScreen = null;
        AbstractScreen cachedNewScreen = registeredStates.get(state);

        if (screens.size() > 0) {
            oldScreen = screens.peek();
        }

        if (cachedNewScreen == null) {
            throw new NullPointerException("No screen registered for this gamestate!");
            // TODO: or create new screen here
        }

        screens.push(cachedNewScreen);

        transitionController.start(oldScreen, cachedNewScreen);
    }

    public void popScreen() {
        if (screens.size() > 0) {
            AbstractScreen oldScreen = screens.pop();
            AbstractScreen newScreen = screens.peek();
            transitionController.start(oldScreen, newScreen);
        }
    }

    public void onUpdate(float delta) {
        Screen screen = screens.peek();
        if (screen != null) {
            screen.update(delta);
        }
    }

    public void onRender(SpriteBatch batch) {
        if (transitionController.doneTransition()) {
            render(batch);
        } else {
            transitionController.render(batch);
        }
    }

    private void render(SpriteBatch batch) {
        Screen screen = screens.peek();
        if (screen != null) {
            screen.render(batch);
        }
    }

    public void onResize(int width, int height) {
        Screen screen = screens.peek();
        if (screen != null) {
            screen.resize(width, height);
        }
    }

    public void onPause() {
        Screen screen = screens.peek();
        if (screen != null) {
            screen.pause();
        }
    }

    public void onResume() {
        Screen screen = screens.peek();
        if (screen != null) {
            screen.resume();
        }
    }

    @Override
    public void dispose() {

        for (AbstractScreen screen : screens) {
            if (screen != null) {
                screen.dispose();
            }
        }
        screens.clear();

        for (AbstractScreen screen : registeredStates.values()) {
            screen.dispose();
        }
        registeredStates.clear();

        transitionController.dispose();
    }
}
