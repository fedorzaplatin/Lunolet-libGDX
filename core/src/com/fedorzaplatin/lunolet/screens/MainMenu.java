package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;
import org.ini4j.Ini;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class MainMenu extends BaseScreen{

    private Stage stage;

    public MainMenu(final MainClass game) {
        super(game);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        this.stage = new Stage(new FitViewport(width, height));

        Image background = new Image((Texture) game.am.get("main-menu/background.png"));

        ImageButton.ImageButtonStyle style = new LunoletButtonsStyle((TextureAtlas) game.am.get("main-menu/startBtn.atlas"));
        ImageButton startBtn = new ImageButton(style);
        startBtn.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int firstStart = 0;
                try {
                    Ini ini = new Ini(new File("config.ini"));
                    Ini.Section section = ini.get("DEFAULT");
                    firstStart = Integer.parseInt(section.get("firstStart"));
                } catch (IOException e){
                    e.printStackTrace();
                }
                if (firstStart == 1) {
                    game.setScreen(game.sm.tutorialScreen);
                } else {
                    game.stopMusic();
                    game.setScreen(game.sm.gameScreen);
                }
            }
        });

        style = new LunoletButtonsStyle((TextureAtlas) game.am.get("main-menu/creditsBtn.atlas"));
        ImageButton creditsBtn = new ImageButton(style);
        creditsBtn.addCaptureListener(new ClickListener() {
            @Override
            public  void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.sm.creditsScreen);
            }
        });

        style = new LunoletButtonsStyle((TextureAtlas) game.am.get("main-menu/exitBtn.atlas"));
        ImageButton exitBtn = new ImageButton(style);
        exitBtn.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.setPosition(0, 14);
        table.setFillParent(true);
        table.left();
        table.pad(38);
        table.add(startBtn).pad(8).left();
        table.row();
        table.add(creditsBtn).pad(8).left();
        table.row();
        table.add(exitBtn).pad(8).left();

        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(4 / 255, 7 / 255, 4 / 255,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
