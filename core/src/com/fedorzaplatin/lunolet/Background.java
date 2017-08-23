package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.fedorzaplatin.lunolet.Constants.PPM;

/**
 * Class describes the game screen's Background
 */

public class Background extends Actor {

    private Texture texture;
    private TextureRegion region;
    private int width, height;
    private float originalWidth;
    private float x;
    private float u, u2;

    public Background(Texture texture, int width, int height) {
        this.texture = texture;
        this.originalWidth = texture.getWidth() / PPM;
        this.texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        region = new TextureRegion(texture);
        region.setRegion(0, 0, 80, 120);
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
