package com.jsoko.screens.transitions;

import com.badlogic.gdx.math.Interpolation;
import com.jsoko.interfaces.ScreenTransition;

public abstract class AbstractTransition implements ScreenTransition {

    protected Interpolation interpolation = Interpolation.linear;
    protected float duration;

    @Override
    public float getDuration() {
        return duration;
    }
}
