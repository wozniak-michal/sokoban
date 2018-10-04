package com.jsoko.screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;

public class SlicingTransition extends AbstractTransition {

    private Direction direction;
    private Array<Integer> slices = new Array<>();
    public SlicingTransition(float duration, Direction direction, int numSlices, Interpolation
            interpolation) {
        this.direction = direction;
        this.interpolation = interpolation;
        this.duration = duration;

        slices.clear();
        for (int i = 0; i < numSlices; i++)
            slices.add(i);
        slices.shuffle();
    }

    @Override
    public void render(SpriteBatch batch, Texture previousScreenTexture, Texture
            nextScreenTexture, float alpha) {
        float width = previousScreenTexture.getWidth();
        float height = previousScreenTexture.getHeight();
        float x, y;
        int sliceWidth = (int) (width / slices.size);

        batch.begin();
        batch.draw(previousScreenTexture, 0, 0, 0, 0, width, height, 1, 1, 0, 0, 0, (int) width,
                   (int) height, false, true);
        if (interpolation != null)
            alpha = interpolation.apply(alpha);
        for (int i = 0; i < slices.size; i++) {

            x = i * sliceWidth;

            float offsetY = height * (1 + slices.get(i) / (float) slices.size);
            switch (direction) {
                case UP:
                    y = -offsetY + offsetY * alpha;
                    break;
                case DOWN:
                    y = offsetY - offsetY * alpha;
                    break;
                case UPDOWN:
                    if (i % 2 == 0) {
                        y = -offsetY + offsetY * alpha;
                    } else {
                        y = offsetY - offsetY * alpha;
                    }
                    break;
                default:
                    y = 0;
            }
            batch.draw(nextScreenTexture, x, y, 0, 0, sliceWidth, nextScreenTexture.getHeight(),
                       1, 1, 0, i * sliceWidth, 0,
                       sliceWidth, nextScreenTexture.getHeight(), false, true);
        }
        batch.end();
    }

    public enum Direction {
        UP, DOWN, UPDOWN
    }
}
