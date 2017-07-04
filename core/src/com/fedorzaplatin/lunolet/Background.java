package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.fedorzaplatin.lunolet.Constants.PPM;

public class Background extends Actor{
    private Texture backgroundTexture;

    public Background(Texture texture) {
        this.backgroundTexture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(backgroundTexture, -800 / PPM / 2, 0, backgroundTexture.getWidth() / PPM, backgroundTexture.getHeight() / PPM);
    }
}
