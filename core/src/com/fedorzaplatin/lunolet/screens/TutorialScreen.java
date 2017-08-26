package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

/**
 * Tutorial screen for the game
 */

public class TutorialScreen extends BaseScreen{

    private Stage stage;
    private Image[] images;
    private ImageButton nextBtn, backBtn, startBtn;
    private int index;

    //Buttons position
    final float x = 0;
    final float y = -220;

    /**
     * Constructor
     * @param game main class that extends class Game
     */
    public TutorialScreen(MainClass game) {
        super(game);

        this.stage = new Stage();

        images = new Image[5];
        images[0] = new Image((Texture) game.am.get("tutorial-screen/tutorial.png"));
        images[1] = new Image((Texture) game.am.get("tutorial-screen/1.png"));
        images[2] = new Image((Texture) game.am.get("tutorial-screen/2.png"));
        images[3] = new Image((Texture) game.am.get("tutorial-screen/3.png"));
        images[4] = new Image((Texture) game.am.get("tutorial-screen/controls.png"));
        index = 0;

        ImageButton.ImageButtonStyle style;
        style = new LunoletButtonsStyle((TextureAtlas) game.am.get("tutorial-screen/nextBtn.atlas"));
        nextBtn = new ImageButton(style);
        nextBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                index++;
                updateScreen();
            }
        });

        style = new LunoletButtonsStyle((TextureAtlas) game.am.get("credits-screen/backBtn.atlas"));
        backBtn = new ImageButton(style);
        backBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                index--;
                updateScreen();
            }
        });

        style = new LunoletButtonsStyle((TextureAtlas) game.am.get("main-menu/startBtn.atlas"));
        startBtn = new ImageButton(style);
        startBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    Ini ini = new Ini(new File("config.ini"));
                    ini.put("DEFAULT", "firstStart", 0);
                    ini.store();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                game.stopMainMenuMusic();
                game.playGameScreenMusic();
                game.setScreen(game.sm.gameScreen);
            }
        });

        stage.addActor(images[0]);
        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(x, y);
        table.center();
        table.add(nextBtn).pad(8);
        stage.addActor(table);
    }

    private void updateScreen() {
        Array<Actor> actors = stage.getActors();
        actors.get(0).remove();
        actors.get(0).remove();

        stage.addActor(images[index]);

        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(x, y);

        if (index == 0) {
            table.add(nextBtn).pad(8);
        } else if (index == 1 | index == 2 | index == 3) {
            table.add(backBtn).pad(8);
            table.add(nextBtn).pad(8);
        } else if (index == 4) {
            table.add(backBtn).pad(8);
            table.add(startBtn).pad(8);
        }
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
