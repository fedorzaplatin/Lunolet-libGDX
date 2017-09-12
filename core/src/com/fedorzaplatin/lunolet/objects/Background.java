package com.fedorzaplatin.lunolet.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.fedorzaplatin.lunolet.Constants.PPM;

/**
 * Class describes the game screen's background
 */

public class Background extends Actor {

    private Texture texture;
    private int width, height;
    private float originalWidth; //Original width of the texture in meters
    private float x;
    private float u, u2;

    /**
     * @param texture background texture
     * @param width width of the texture in pixels
     * @param height height of the texture in pixels
     */
    public Background(Texture texture, int width, int height) {
        this.texture = texture;
        this.texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        this.originalWidth = texture.getWidth() / PPM;
        this.width = width;
        this.height = height;
        this.x = 0;
        this.u = 0;
        this.u2 = width / originalWidth;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, x, 0, width, height, u, 1, u2, 0);
    }

    /**
     * Extends background texture left or right regarding to the sign of the `value`
     * @param value amount of extending in meters. `value` < 0 - extends left. `value` > 0 extends right.
     */
    public void extend(float value) {
        width += Math.abs(value);

        if (value < 0) {
            x += value;
            u += value / originalWidth;
        } else if (value > 0) {
            u2 += value / originalWidth;
        }
    }
}
