package com.jsoko.screens.transitions;

import com.badlogic.gdx.math.Interpolation;

public class Transition {

    public static SlidingTransition SlideUp = new SlidingTransition(
            0.7f, SlidingTransition.Direction.UP, Interpolation.pow3, false);

    public static SlidingTransition SlideDown = new SlidingTransition(
            0.7f, SlidingTransition.Direction.DOWN, Interpolation.pow3, false);
}
