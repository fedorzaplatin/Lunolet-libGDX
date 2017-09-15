package com.fedorzaplatin.lunolet.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LunoletButtonsStyle extends ImageButton.ImageButtonStyle {

    /**
     * Buttons style constructor
     * @param atlas Texture atlas which contains a button textures (up, over, down)
     * @param buttonName Name of a button
     */
    public LunoletButtonsStyle(TextureAtlas atlas, String buttonName) {
        super();
        ImageResolver.TextureAtlasImageResolver tair = new ImageResolver.TextureAtlasImageResolver(atlas);
        this.up = new TextureRegionDrawable(tair.getImage(buttonName + "Up"));
        this.over = new TextureRegionDrawable(tair.getImage(buttonName + "Over"));
        this.down = new TextureRegionDrawable(tair.getImage(buttonName + "Down"));
    }
}
