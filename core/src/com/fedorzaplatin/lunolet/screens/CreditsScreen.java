package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.MainClass;

public class CreditsScreen extends BaseScreen {

    private final float WIDTH = Gdx.graphics.getWidth();
    private final float HEIGTH = Gdx.graphics.getHeight();

    private Stage stage;

    public CreditsScreen(final MainClass game) {
        super(game);

        stage = new Stage(new FitViewport(WIDTH, HEIGTH));

        Skin skin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));
        Label text = new Label("Lunolet\n" +
                "Open source project by Fedor Zapaltin\n" +
                "Source code: github.com/fedorzaplatin/lunolet-libgdx\n" +
                "UI skin by czyzby: github.com/czyzby/gdx-skins/tree/master/cloud-form", skin);
        text.setAlignment(Align.center);
        text.setPosition(WIDTH / 2 - text.getWidth() / 2, HEIGTH / 2 - text.getHeight() / 2);

        TextButton backBtn = new TextButton("Back", skin);
        backBtn.setPosition(20, 550);
        backBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        stage.addActor(text);
        stage.addActor(backBtn);
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
