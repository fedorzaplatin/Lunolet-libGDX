package com.fedorzaplatin.lunolet.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;

public class PauseMenu extends Stage {

    private int command; // 0 - keep pause, 1 - resume game, 2 - main menu, 3 - exit

    /**
     * Constructor
     * @param viewport
     * @param buttons TextureAtlas that contains buttons textures (resume, mainMenu, exit)
     * @param font BitmapFont which will be used to create heading label
     */
    public PauseMenu(Viewport viewport, TextureAtlas buttons, BitmapFont font) {
        super(viewport);
        this.command = 0;

        // Create heading label
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label headingLabel = new Label("pause", labelStyle);

        // Create resume button
        ImageButton.ImageButtonStyle style;
        style = new LunoletButtonsStyle(buttons, "resume");
        ImageButton resumeBtn = new ImageButton(style);
        resumeBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                command = 1;
            }
        });

        // Create main menu button
        style = new LunoletButtonsStyle(buttons, "mainMenu");
        ImageButton mainMenuBtn = new ImageButton(style);
        mainMenuBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                command = 2;
            }
        });

        // Create exit button
        style = new LunoletButtonsStyle(buttons, "exit");
        ImageButton exitBtn = new ImageButton(style);
        exitBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                command = 3;
            }
        });

        // Create layout table
        Table table = new Table();
        table.setFillParent(true);
        table.add(headingLabel).top().pad(26);
        table.row();
        table.add(resumeBtn).pad(8);
        table.row();
        table.add(mainMenuBtn).pad(8);
        table.row();
        table.add(exitBtn).pad(8);

        addActor(table);
    }

    @Override
    public boolean keyDown(int keyCode) {
        boolean result = super.keyDown(keyCode);

        // If Escape key pressed which the command to 'resume' (1)
        if (keyCode == Input.Keys.ESCAPE) {
            command = 1;
            return true;
        }

        return result;
    }

    public int getCommand() {
        return command;
    }

    public void reset() {
        command = 0;
    }
}
