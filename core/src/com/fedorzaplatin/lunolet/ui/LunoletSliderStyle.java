package com.fedorzaplatin.lunolet.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LunoletSliderStyle extends Slider.SliderStyle{

    /**
     * Buttons style constructor
     * @param atlas Texture atlas which contains a slider textures (background, knob, knobOver, knobDown)
     */
    public LunoletSliderStyle(TextureAtlas atlas) {
        ImageResolver.TextureAtlasImageResolver tair = new ImageResolver.TextureAtlasImageResolver(atlas);
        this.background = new TextureRegionDrawable(tair.getImage("background"));
        this.knob = new TextureRegionDrawable(tair.getImage("knob"));
        this.knobOver = new TextureRegionDrawable(tair.getImage("knobOver"));
        this.knobDown = new TextureRegionDrawable(tair.getImage("knobDown"));
    }
}
