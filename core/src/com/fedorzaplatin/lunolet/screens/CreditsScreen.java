package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;

public class CreditsScreen extends BaseScreen {

    private Stage stage;

    public CreditsScreen(final MainClass game) {
        super(game);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        stage = new Stage(new FitViewport(width, height));

        Label.LabelStyle labelStyle;
        labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas52.fnt"), Color.WHITE);
        Label creditsLabel = new Label("Credits", labelStyle);

        labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/courierNew30.fnt"), Color.WHITE);
        String text = "Developer\n" +
                "Fedor Zaplatin\n\n" +
                "Music\n" +
                "Borrtex - Our Home\n" +
                "Parvus Decree - Space Travel\n\n" +
                "Source code\n" +
                "github.com/fedorzaplatin/lunolet-libgdx";
        Label textLabel = new Label(text, labelStyle);
        textLabel.setAlignment(Align.center);

        ImageButton backBtn = new ImageButton(new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "back"));
        backBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.sm.mainMenu);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(creditsLabel).pad(26).expandX().center().top();
        table.row().expand();
        table.add(textLabel).center();
        table.row().expand();
        table.add(backBtn).pad(23).expandX().bottom().left();
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0,0, 0);
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
    }
}
