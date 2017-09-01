package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;

public class GameCompletedScreen extends BaseScreen {

    private Stage stage;

    public GameCompletedScreen(final MainClass game) {
        super(game);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Label.LabelStyle labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas52.fnt"), Color.WHITE);
        Label textLabel = new Label("congratulations!\nlanding   is   successful", labelStyle);
        textLabel.setAlignment(Align.center);

        ImageButton.ImageButtonStyle style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "again");
        ImageButton againBtn = new ImageButton(style);
        againBtn.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.sm.gameScreen);
            }
        });

        style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "mainMenu");
        ImageButton mainMenuBtn = new ImageButton(style);
        mainMenuBtn.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.stopGameScreenMusic();
                game.playMainMenuMusic();
                game.setScreen(game.sm.mainMenu);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(textLabel).colspan(2);
        table.row();
        table.add(mainMenuBtn).pad(23);
        table.add(againBtn).pad(23);

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
