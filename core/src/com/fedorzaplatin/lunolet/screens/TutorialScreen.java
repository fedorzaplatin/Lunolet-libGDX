package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    private Table[] tables;
    private int index;

    /**
     * Constructor
     * @param game the main class of a game that extends class Game
     */
    public TutorialScreen(final MainClass game) {
        super(game);
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.tables = new Table[5]; // Initialize an array which will contains pages of tutorial as tables
        TextureAtlas textures = (TextureAtlas) game.am.get("tutorial-screen/tutorialScreenTextures.atlas");

        // Create labels styles for text
        Label.LabelStyle style1 = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas63.fnt"), Color.WHITE);
        Label.LabelStyle style2 = new Label.LabelStyle((BitmapFont) game.am.get("fonts/courierNew30.fnt"), Color.WHITE);
        Label.LabelStyle style3 = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas52.fnt"), Color.WHITE);

        // Create first page of the tutorial
        // Create labels
        Label pageOneHeaderLabel = new Label("tutorial", style3);
        Label pageOneTextLabel = new Label("Your goal is to land on the\nmoon successfully\n" +
                "\n" +
                "To do this you have to remember\na few things", style2);
        pageOneTextLabel.setAlignment(Align.center);

        // Create table
        tables[0] = new Table();
        tables[0].setFillParent(true);
        tables[0].center();
        tables[0].add(pageOneHeaderLabel).padTop(52);
        tables[0].row().expand();
        tables[0].add(pageOneTextLabel).center();
        tables[0].row();
        tables[0].add(createNextButton()).pad(23).right();

        // Create second page of the tutorial
        // Create labels
        Label digitOneLabel = new Label("1.", style1);
        Label pageTwoTextLabel = new Label("You have to land only\nonto horizontal planes", style2);

        // Create subtable with text labels
        Table pageTwoLabels = new Table();
        pageTwoLabels.add(digitOneLabel).padLeft(70).padRight(65);
        pageTwoLabels.add(pageTwoTextLabel).left();

        // Create images
        Image surfaceRight = new Image((TextureRegion) textures.findRegion("surfaceRight"));
        Image surfaceWrong = new Image((TextureRegion) textures.findRegion("surfaceWrong"));

        // Create subtable with images
        Table pageTwoImages = new Table();
        pageTwoImages.add(surfaceRight).padRight(50).expandX();
        pageTwoImages.add(surfaceWrong).padLeft(50).expandX();

        // Create main table
        tables[1] = new Table();
        tables[1].setFillParent(true);
        tables[1].center();
        tables[1].add(pageTwoLabels).colspan(2).padTop(58).left().top();
        tables[1].row().expand();
        tables[1].add(pageTwoImages).colspan(2).center();
        tables[1].row().bottom();
        tables[1].add(createBackButton()).pad(23).left();
        tables[1].add(createNextButton()).pad(23).right();

        // Create third page of the tutorial
        // Create labels
        Label digitTwo = new Label("2.", style1);
        Label pageThreeTextLabel = new Label("While a landing the angle\nshould be less than 13˚", style2);

        // Create subtable with text labels
        Table pageThreeLabels = new Table();
        pageThreeLabels.add(digitTwo).padLeft(70).padRight(65);
        pageThreeLabels.add(pageThreeTextLabel);

        // Create images
        Image angleRight = new Image((TextureRegion) textures.findRegion("angleRight"));
        Image angleWrong = new Image((TextureRegion) textures.findRegion("angleWrong"));

        // Create subtable with images
        Table pageThreeImages = new Table();
        pageThreeImages.add(angleRight).padRight(50).expandY();
        pageThreeImages.add(angleWrong).padLeft(50).expandY();

        // Create main table
        tables[2] = new Table();
        tables[2].setFillParent(true);
        tables[2].center();
        tables[2].add(pageThreeLabels).colspan(2).padTop(58).left().top();
        tables[2].row().expand();
        tables[2].add(pageThreeImages).colspan(2).center();
        tables[2].row().bottom();
        tables[2].add(createBackButton()).pad(23).left();
        tables[2].add(createNextButton()).pad(23).right();

        // Create fourth page of the tutorial
        // Create labels
        Label digitThree = new Label("3.", style1);
        Label pageFourTextLabel = new Label("While landing the velocity\nshould be less than 3 m/s", style2);

        // Create subtable with labels
        Table pageFourLabels = new Table();
        pageFourLabels.add(digitThree).padLeft(70).padRight(65);
        pageFourLabels.add(pageFourTextLabel);

        // Create an image
        Image velocity = new Image((TextureRegion) textures.findRegion("velocity"));

        tables[3] = new Table();
        tables[3].setFillParent(true);
        tables[3].center();
        tables[3].add(pageFourLabels).colspan(2).padTop(58).left().top();
        tables[3].row().expand();
        tables[3].add(velocity).colspan(2).center();
        tables[3].row().bottom();
        tables[3].add(createBackButton()).pad(23).left();
        tables[3].add(createNextButton()).pad(23).right();

        // Create fifth page of the tutorial
        // Create "Start" button
        ImageButton.ImageButtonStyle buttonStyle;
        buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "start");
        ImageButton startBtn = new ImageButton(buttonStyle);
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

        // Create labels
        Label pageFiveHeaderLabel = new Label("controls", style3);
        Label keyALabel = new Label("- Rotate the lunar module left", style2);
        Label keyBLabel = new Label("- Rotate the lunar module right", style2);
        Label keySpaceLabel = new Label("- Activate the main engine", style2);

        // Create images
        Image keyAImage = new Image((TextureRegion) textures.findRegion("a"));
        Image keyBImage = new Image((TextureRegion) textures.findRegion("d"));
        Image keySpaceImage = new Image((TextureRegion) textures.findRegion("space"));

        // Create subtable with images and labels
        Table controlsTable = new Table();
        controlsTable.add(keyAImage).padBottom(29).right();
        controlsTable.add(keyALabel).padLeft(22).left();
        controlsTable.row();
        controlsTable.add(keyBImage).padBottom(29).right();
        controlsTable.add(keyBLabel).padLeft(22).left();
        controlsTable.row();
        controlsTable.add(keySpaceImage).padBottom(29).right();
        controlsTable.add(keySpaceLabel).padLeft(22).left();

        // Create main table
        tables[4] = new Table();
        tables[4].setFillParent(true);
        tables[4].center();
        tables[4].add(pageFiveHeaderLabel).colspan(2).padTop(52);
        tables[4].row().expand();
        tables[4].add(controlsTable).colspan(2).center();
        tables[4].row();
        tables[4].add(createBackButton()).pad(23).left();
        tables[4].add(startBtn).pad(23).right();
    }

    /**
     * Update the stage. Remove current table from the stage and add table with index `index` into the stage
     */
    private void updateStage() {
        Array<Actor> actors = stage.getActors();

        // If there's no table in the stage add table with index `index` into the stage
        try {
            actors.get(0).remove();
            stage.addActor(tables[index]);
        } catch (IndexOutOfBoundsException e) {
            stage.addActor(tables[index]);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        this.index = 0;
        updateStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and draw stage
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

    private ImageButton createBackButton() {
        // Create "Back" button
        LunoletButtonsStyle buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "back");
        ImageButton backBtn = new ImageButton(buttonStyle);
        backBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                index--;
                updateStage();
            }
        });
        return backBtn;
    }

    private ImageButton createNextButton() {
        // Create "Next" button
        LunoletButtonsStyle buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "next");
        ImageButton nextBtn = new ImageButton(buttonStyle);
        nextBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                index++;
                updateStage();
            }
        });
        return nextBtn;
    }
}
