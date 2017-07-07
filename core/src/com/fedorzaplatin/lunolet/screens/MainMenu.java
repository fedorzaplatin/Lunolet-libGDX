package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.MainClass;

public class MainMenu extends BaseScreen{

    private Stage stage;

    public MainMenu(final MainClass game) {
        super(game);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        this.stage = new Stage(new FitViewport(width, height));

        Image background = new Image(new Texture("main-menu/background.png"));

        Image startBtn = new Image(new Texture("main-menu/startBtn.png"));
        startBtn.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.sm.gameScreen);
            }
        });

        Image creditsBtn = new Image(new Texture("main-menu/creditsBtn.png"));
        creditsBtn.addCaptureListener(new ClickListener() {
            @Override
            public  void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.sm.creditsScreen);
            }
        });

        Image exitBtn = new Image(new Texture("main-menu/exitBtn.png"));
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
