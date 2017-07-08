package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;

public class GameOverScreen extends BaseScreen{

    private Stage stage;

    public GameOverScreen(final MainClass game) {
        super(game);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Image background = new Image(new Texture("game-over-screen/background.png"));

        ImageButton againBtn = new ImageButton(new LunoletButtonsStyle("game-over-screen/againBtnUp.png",
                "game-over-screen/againBtnDown.png",
                "game-over-screen/againBtnOver.png"));
        againBtn.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.sm.gameScreen);
            }
        });

        ImageButton mainMenuBtn = new ImageButton(new LunoletButtonsStyle("game-over-screen/mainMenuBtnUp.png",
                "game-over-screen/mainMenuBtnDown.png",
                "game-over-screen/mainMenuBtnOver.png"));
        mainMenuBtn.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.sm.mainMenu);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(0, -140);
        table.center();
        table.add(againBtn).pad(8);
        table.add(mainMenuBtn).pad(8);

        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
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
        stage.dispose();
        super.dispose();
    }
}
