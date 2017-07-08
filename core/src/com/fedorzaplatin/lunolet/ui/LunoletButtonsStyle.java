package com.fedorzaplatin.lunolet.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class LunoletButtonsStyle extends ImageButton.ImageButtonStyle{
    public LunoletButtonsStyle(String upSprite, String downSprite, String overSprite) {
        super();
        this.up = (new Image(new Texture(upSprite))).getDrawable();
        this.down = (new Image(new Texture(downSprite))).getDrawable();
        this.over = (new Image(new Texture(overSprite))).getDrawable();
    }
}
