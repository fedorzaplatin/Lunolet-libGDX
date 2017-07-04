package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.MainClass;

public class MainMenu extends BaseScreen{

    private final float WIDTH = Gdx.graphics.getWidth();
    private final float HEIGHT = Gdx.graphics.getHeight();

    private Stage stage;

    public MainMenu(final MainClass game) {
        super(game);

        this.stage = new Stage(new FitViewport(WIDTH, HEIGHT));

        Skin skin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));

        TextButton playBtn = new TextButton("Play", skin);
        playBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.sm.get("game"));
            }
        });

        TextButton creditsButton = new TextButton("Credits", skin);
        creditsButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.sm.get("credits"));
            }
        });

        TextButton exitBtn = new TextButton("Exit", skin);
        exitBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(playBtn);
        table.row();
        table.add(creditsButton);
        table.row();
        table.add(exitBtn);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
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

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
