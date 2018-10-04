package com.jsoko.screens.transitions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class ColorFadeTransition extends AbstractTransition {

    private final Color color;
    private final Texture texture;

    public ColorFadeTransition(float duration, Color color, Interpolation interpolation) {
        this.color = new Color(Color.WHITE);
        this.interpolation = interpolation;
        this.duration = duration;

        texture = new Texture(1, 1, Pixmap.Format.RGBA8888);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, 1, 1);
        texture.draw(pixmap, 0, 0);
    }

    @Override
    public void render(SpriteBatch batch, Texture previousScreenTexture, Texture
            nextScreenTexture, float alpha) {
        float width = previousScreenTexture.getWidth();
        float height = previousScreenTexture.getHeight();
        alpha = interpolation.apply(alpha);

        batch.begin();

        float fade = alpha * 2;

        if (fade > 1.0f) {
            fade = 1.0f - (alpha * 2 - 1.0f);
            color.a = 1.0f - fade;
            batch.setColor(color);

            batch.draw(nextScreenTexture, 0, 0, width / 2, height / 2, nextScreenTexture.getWidth
                               (), nextScreenTexture.getHeight(),
                       1, 1, 0, 0, 0, nextScreenTexture.getWidth(), nextScreenTexture.getHeight()
                    , false, true);

        } else {

            color.a = 1.0f - fade;
            batch.setColor(color);
            batch.draw(previousScreenTexture, 0, 0, width / 2, height / 2, width, height,
                       1, 1, 0, 0, 0, (int) width, (int) height, false, true);
        }

        color.a = fade;

        batch.setColor(color);
        batch.draw(texture, 0, 0, width, height);
        batch.end();
        batch.setColor(Color.WHITE);

    }
}
