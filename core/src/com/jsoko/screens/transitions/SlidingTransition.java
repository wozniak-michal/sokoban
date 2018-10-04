package com.jsoko.screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class SlidingTransition extends AbstractTransition {

    private Direction direction;
    private boolean slideOut;
    public SlidingTransition(float duration, Direction direction, Interpolation interpolation,
                             boolean slideOut) {
        this.direction = direction;
        this.interpolation = interpolation;
        this.slideOut = slideOut;
        this.duration = duration;
    }

    @Override
    public void render(SpriteBatch batch, Texture previousScreenTexture, Texture
            nextScreenTexture, float alpha) {
        float width = previousScreenTexture.getWidth();
        float height = previousScreenTexture.getHeight();
        float x = 0;
        float y = 0;
        if (interpolation != null)
            alpha = interpolation.apply(alpha);

        switch (direction) {
            case LEFT:
                x = -width * alpha;
                if (!slideOut)
                    x += width;
                break;
            case RIGHT:
                x = width * alpha;
                if (!slideOut)
                    x -= width;
                break;
            case UP:
                y = height * alpha;
                if (!slideOut)
                    y -= height;
                break;
            case DOWN:
                y = -height * alpha;
                if (!slideOut)
                    y += height;
                break;
        }
        Texture texBottom = slideOut ? nextScreenTexture : previousScreenTexture;
        Texture texTop = slideOut ? previousScreenTexture : nextScreenTexture;

        batch.begin();
        batch.draw(texBottom, 0, 0, 0, 0, width, height, 1, 1, 0, 0, 0, (int) width, (int)
                height, false, true);
        batch.draw(texTop, x, y, 0, 0, nextScreenTexture.getWidth(), nextScreenTexture.getHeight
                           (), 1, 1, 0, 0, 0,
                   nextScreenTexture.getWidth(), nextScreenTexture.getHeight(), false, true);
        batch.end();
    }


    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
