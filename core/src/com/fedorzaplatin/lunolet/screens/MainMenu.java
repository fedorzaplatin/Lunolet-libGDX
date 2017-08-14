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

public class MainMenu extends BaseScreen{

    private Stage stage;
    private Music music;

    public MainMenu(final MainClass game) {
        super(game);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        this.stage = new Stage(new FitViewport(width, height));

        music = game.am.get("main-menu/mainMenuMusic.mp3");
        music.setLooping(true);

        Image background = new Image((Texture) game.am.get("main-menu/background.png"));

        ImageButton.ImageButtonStyle style = new LunoletButtonsStyle((TextureAtlas) game.am.get("main-menu/startBtn.atlas"));
        ImageButton startBtn = new ImageButton(style);
        startBtn.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.sm.gameScreen);
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
        music.play();
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
        music.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
        music.dispose();
        super.dispose();
    }
}
