package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.MainClass;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen extends BaseScreen{

    private float time = 0;
    private Stage stage;
    private Image firstImage, secondImage;
    private Label loadingProgress;

    public SplashScreen(MainClass game) {
        super(game);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        stage = new Stage(new FitViewport(width, height));

        firstImage = new Image(new Texture("splash-screen/splashImageFZ.png"));
        secondImage = new Image(new Texture("splash-screen/splashImageLunolet.png"));

        loadingProgress = new Label("Loading...    0%", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/bebas28.fnt")), Color.WHITE));
        loadingProgress.setAlignment(Align.left);
        loadingProgress.setPosition(10, 10);

        stage.addActor(secondImage);
        stage.addActor(loadingProgress);
        stage.addActor(firstImage);
    }

    @Override
    public void show() {
        firstImage.addAction(sequence(delay(2f), fadeOut(1f)));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        time += delta;

        if (game.am.update() & time > 7) {
            game.finishLoad();
        } else {
            int progress = (int) (game.am.getProgress() * 100);
            loadingProgress.setText(String.format("Loading...    %3d", progress) + "%");
        }

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
        super.hide();
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
