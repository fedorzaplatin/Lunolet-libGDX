package com.fedorzaplatin.lunolet.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LunoletButtonsStyle extends ImageButton.ImageButtonStyle{
    public LunoletButtonsStyle(TextureAtlas atlas) {
        super();
        ImageResolver.TextureAtlasImageResolver tair = new ImageResolver.TextureAtlasImageResolver(atlas);
        this.up = new TextureRegionDrawable(tair.getImage("btnUp"));
        this.over = new TextureRegionDrawable(tair.getImage("btnOver"));
        this.down = new TextureRegionDrawable(tair.getImage("btnDown"));
    }
}
