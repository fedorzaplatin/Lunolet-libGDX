package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;
import com.fedorzaplatin.lunolet.ui.LunoletSliderStyle;
import org.ini4j.Ini;

import java.io.IOException;

/**
 * Main menu
 */
public class MainMenu extends BaseScreen{

    private int state;
    private Stage menu, tutorial, credits, settings, statistics;
    final private Ini config;
    private int firstStart;
    private Music music;

    public MainMenu(final MainClass game, final Ini config) {
        super(game);
        this.config = config;
        this.music = game.am.get("main-menu/mainMenuMusic.mp3");
        this.music.setLooping(true);

        Ini.Section section = config.get("DEFAULT");
        firstStart = Integer.parseInt(section.get("firstStart"));

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        menu = new Menu(new FitViewport(width, height));
        tutorial = new Tutorial(new FitViewport(width, height));
        credits = new Credits(new FitViewport(width, height));
        settings = new Settings(new FitViewport(width, height));
        statistics = new Statistics(new FitViewport(width, height));
    }

    @Override
    public void show() {
        music.play();
        updateScreen(0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(4 / 255, 7 / 255, 4 / 255,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (state == 0){
            menu.act();
            menu.draw();
        } else if (state == 2) {
            tutorial.act();
            tutorial.draw();
        } else if (state == 3) {
            settings.act();
            settings.draw();
        } else if (state == 4) {
            credits.act();
            credits.draw();
        } else if (state == 6) {
            statistics.act();
            statistics.draw();
        }
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
        music.stop();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        menu.dispose();
        settings.dispose();
        credits.dispose();
        tutorial.dispose();
        super.dispose();
    }

    public void setMusicVolume(float value) {
        music.setVolume(value);
    }

    /**
     * Set screen stage accordance with stateNumber
     * @param stateNumber 0 - main menu, 1 - game, 2 - tutorial, 3 - settings, 4 - credits, 5 - exit the game, 6 - statistics screen
     */
    private void updateScreen(int stateNumber) {
        switch (stateNumber) {
            case 0: // main menu
                state = 0;
                Gdx.input.setInputProcessor(menu);
                break;
            case 1: // game
                if (firstStart == 1) {
                    firstStart = 0;
                    config.put("DEFAULT", "firstStart", 0);
                    try {
                        config.store();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    updateScreen(2);
                } else {
                    state = 1;
                    game.setScreen(game.sm.gameScreen);
                }
                break;
            case 2: // tutorial
                state = 2;
                Gdx.input.setInputProcessor(tutorial);
                break;
            case 3: // settings
                state = 3;
                ((Settings) settings).reset();
                Gdx.input.setInputProcessor(settings);
                break;
            case 4: // credits
                state = 4;
                Gdx.input.setInputProcessor(credits);
                break;
            case 5: // exit the game
                Gdx.app.exit();
                break;
            case 6:
                state = 6;

                // Get statistics from configuration file
                int successfulLandings, failedLandings;
                Ini.Section section = config.get("STATISTICS");
                successfulLandings = Integer.parseInt(section.get("successfulLandings"));
                failedLandings = Integer.parseInt(section.get("failedLandings"));

                // Update statistics screen
                ((Statistics) statistics).refresh(failedLandings, successfulLandings);

                Gdx.input.setInputProcessor(statistics);
                break;
        }
    }

    /**
     * Main menu
     */
    private class Menu extends Stage{
        public Menu(Viewport viewport) {
            super(viewport);
            ImageButton.ImageButtonStyle buttonStyle;

            // Create background
            Image background = new Image((Texture) game.am.get("main-menu/background.png"));

            // Create start button
            ImageButton.ImageButtonStyle style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "start");
            ImageButton startBtn = new ImageButton(style);
            startBtn.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateScreen(1);
                }
            });

            // Create tutorial button
            style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "tutorial");
            ImageButton tutorialBtn = new ImageButton(style);
            tutorialBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    updateScreen(2);
                }
            });

            // Create settings button
            style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "settings");
            ImageButton settingsBtn = new ImageButton(style);
            settingsBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    updateScreen(3);
                }
            });

            // Create credits button
            style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "credits");
            ImageButton creditsBtn = new ImageButton(style);
            creditsBtn.addCaptureListener(new ClickListener() {
                @Override
                public  void clicked(InputEvent event, float x, float y) {
                    updateScreen(4);
                }
            });

            // Create exit button
            style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "exit");
            ImageButton exitBtn = new ImageButton(style);
            exitBtn.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateScreen(5);
                }
            });

            // Create table
            Table table = new Table();
            table.setFillParent(true);
            table.left();
            table.pad(38);
            table.padTop(150);
            table.add(startBtn).pad(8).left();
            table.row();
            table.add(tutorialBtn).pad(8).left();
            table.row();
            table.add(settingsBtn).pad(8).left();
            table.row();
            table.add(creditsBtn).pad(8).left();
            table.row();
            table.add(exitBtn).pad(8).left();

            // Add table to the stage
            addActor(background);
            addActor(table);
        }
    }

    /**
     * Tutorial screen
     */
    private class Tutorial extends Stage{

        private Table[] tables;
        private int index;

        public Tutorial(Viewport viewport) {
            super(viewport);
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
                    index = 0;
                    updateStage();
                    updateScreen(1);
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

            this.index = 0;
            updateStage();

        }

        /**
        * Update the stage. Remove current table from the stage and add table with index `index` into the stage
        */
        private void updateStage() {
            Array<Actor> actors = getActors();

            // If there's no table in the stage add table with index `index` into the stage
            try {
                actors.get(0).remove();
                addActor(tables[index]);
            } catch (IndexOutOfBoundsException e) {
                addActor(tables[index]);
            }
        }

        /**
         * Create back button
         * @return create button
         */
        private ImageButton createBackButton() {
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

        /**
         * Create next button
         * @return create button
         */
        private ImageButton createNextButton() {
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

    /**
     * Credits screen
     */
    private class Credits extends Stage {
        public Credits(Viewport viewport) {
            super(viewport);

            Label.LabelStyle labelStyle;
            ImageButton.ImageButtonStyle buttonStyle;

            // Create heading label
            labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas52.fnt"), Color.WHITE);
            Label creditsLabel = new Label("Credits", labelStyle);

            // Create text label
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

            // Create back button
            buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "back");
            ImageButton backBtn = new ImageButton(buttonStyle);
            backBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    updateScreen(0);
                }
            });

            // Create table
            Table table = new Table();
            table.setFillParent(true);
            table.add(creditsLabel).pad(26).expandX().center().top();
            table.row().expand();
            table.add(textLabel).center();
            table.row().expand();
            table.add(backBtn).pad(23).expandX().bottom().left();

            // Add table to the stage
            addActor(table);
        }
    }

    /**
     * Settings screen
     */
    private class Settings extends Stage {

        private boolean changed;
        private ImageButton applyBtn;
        private Slider musicVolumeSlider, effectsVolumeSlider;
        private float currentMusicVolume, currentEffectsVolume;

        public Settings(Viewport viewport) {
            super(viewport);
            this.changed = false;
            Label.LabelStyle labelStyle;
            Slider.SliderStyle sliderStyle;
            ImageButton.ImageButtonStyle buttonStyle;

            // Get current music and effects volume
            Ini.Section section = config.get("SETTINGS");
            this.currentMusicVolume = Float.parseFloat(section.get("musicVolume"));
            this.currentEffectsVolume = Float.parseFloat(section.get("effectsVolume"));

            // Create heading label
            labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas52.fnt"), Color.WHITE);
            Label settingsLabel = new Label("settings", labelStyle);

            // Create music volume label
            labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/courierNew30.fnt"), Color.WHITE);
            Label musicVolumeLabel = new Label("Music volume", labelStyle);

            // Create music volume slider
            sliderStyle = new LunoletSliderStyle((TextureAtlas) game.am.get("settings-screen/slider.atlas"));
            musicVolumeSlider = new Slider(0, 1, 0.01f, false, sliderStyle);

            // Create effects volume label
            Label effectsVolumeLabel = new Label("Effects volume", labelStyle);

            // Create effects volume slider
            sliderStyle = new LunoletSliderStyle((TextureAtlas) game.am.get("settings-screen/slider.atlas"));
            effectsVolumeSlider = new Slider(0, 1, 0.01f, false, sliderStyle);

            // Create table contains settings parameters
            Table parametersTable = new Table();
            parametersTable.add(musicVolumeLabel).pad(46);
            parametersTable.add(musicVolumeSlider).pad(46);
            parametersTable.row();
            parametersTable.add(effectsVolumeLabel).pad(46);
            parametersTable.add(effectsVolumeSlider).pad(46);

            // Create back button
            buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "back");
            ImageButton backBtn = new ImageButton(buttonStyle);
            backBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setMusicVolume(currentMusicVolume);
                    game.setEffectsVolume(currentEffectsVolume);
                    updateScreen(0);
                }
            });

            // Create statistics button
            buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "statistics");
            ImageButton statisticsBtn = new ImageButton(buttonStyle);
            statisticsBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    updateScreen(6);
                }
            });

            // Create apply button
            buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "apply");
            applyBtn = new ImageButton(buttonStyle);
            applyBtn.setVisible(false);
            applyBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    config.put("SETTINGS", "musicVolume", musicVolumeSlider.getValue());
                    config.put("SETTINGS", "effectsVolume", effectsVolumeSlider.getValue());
                    try {
                        config.store();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentMusicVolume = musicVolumeSlider.getValue();
                    currentEffectsVolume = effectsVolumeSlider.getValue();
                }
            });

            // Create main table
            Table mainTable = new Table();
            mainTable.setFillParent(true);
            mainTable.center().top();
            mainTable.add(settingsLabel).pad(26).expandX().center().top().colspan(3);
            mainTable.row().expand();
            mainTable.add(parametersTable).colspan(3);
            mainTable.row();
            mainTable.add(backBtn).pad(23).left().bottom();
            mainTable.add(statisticsBtn).pad(23).center().bottom();
            mainTable.add(applyBtn).pad(23).right().bottom();

            // Add main table to the stage
            reset();
            addActor(mainTable);
        }

        @Override
        public void act() {
            if ((currentMusicVolume != musicVolumeSlider.getValue() | currentEffectsVolume != effectsVolumeSlider.getValue()) & !changed) {
                applyBtn.setVisible(true);
                changed = true;
            }

            if (currentMusicVolume == musicVolumeSlider.getValue() & currentEffectsVolume == effectsVolumeSlider.getValue() & changed) {
                applyBtn.setVisible(false);
                changed = false;
            }

            if (changed){
                if (currentMusicVolume != musicVolumeSlider.getValue()){
                    game.setMusicVolume(musicVolumeSlider.getValue());
                }
                if (currentEffectsVolume != effectsVolumeSlider.getValue()) {
                    game.setEffectsVolume(effectsVolumeSlider.getValue());
                }
            }

            super.act();
        }

        public void reset() {
            musicVolumeSlider.setValue(currentMusicVolume);
            effectsVolumeSlider.setValue(currentEffectsVolume);
        }
    }

    /**
     * Statistics screen
     */
    private class Statistics extends Stage {

        private int failedLandings, successfulLandings;
        private Label failedLandingsCounter, successfulLandingsCounter;

        public Statistics(Viewport viewport) {
            super(viewport);

            Label.LabelStyle labelStyle;
            ImageButton.ImageButtonStyle buttonStyle;

            // Create header label
            labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas52.fnt"), Color.WHITE);
            Label headerLabel = new Label("statistics", labelStyle);

            // Create successful landings label
            labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/courierNew30.fnt"), Color.WHITE);
            Label successfulLandingsLabel = new Label("Successful landings:", labelStyle);

            // Create successful landings counter label
            successfulLandingsCounter = new Label(String.format("%d", successfulLandings), labelStyle);

            // Create failed landings label
            Label failedLandingsLabel = new Label("Failed landings:", labelStyle);

            // Create failed landings counter label
            failedLandingsCounter = new Label(String.format("%d", failedLandings), labelStyle);

            // Create back button
            buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "back");
            ImageButton backBtn = new ImageButton(buttonStyle);
            backBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    updateScreen(3);
                }
            });

            // Create reset statistics button
            buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "resetStatistics");
            ImageButton resetStatisticsBtn = new ImageButton(buttonStyle);
            resetStatisticsBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Reset statistics values
                    config.put("STATISTICS", "successfulLandings", 0);
                    config.put("STATISTICS", "failedLandings", 0);
                    try {
                        config.store();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    failedLandingsCounter.setText("0");
                    successfulLandingsCounter.setText("0");
                }
            });

            // Create subtable with statistics information
            Table statisticsTable = new Table();
            statisticsTable.setFillParent(false);
            statisticsTable.add(successfulLandingsLabel).pad(20).right();
            statisticsTable.add(successfulLandingsCounter).pad(20).left();
            statisticsTable.row();
            statisticsTable.add(failedLandingsLabel).pad(20).right();
            statisticsTable.add(failedLandingsCounter).pad(20).left();

            // Create main table
            Table table = new Table();
            table.setFillParent(true);
            table.add(headerLabel).colspan(2).pad(26).expandX().center().top();
            table.row().expand();
            table.add(statisticsTable).colspan(3);
            table.row();
            table.add(backBtn).pad(23).expandX().bottom().left();
            table.add(resetStatisticsBtn).pad(23).expandX().bottom().right();

            // Add main table to the stage
            addActor(table);
        }

        /**
         * @param fl number of failed landings
         * @param sl number of successful landings
         */
        public void refresh(int fl, int sl) {
            successfulLandings = sl;
            failedLandings = fl;

            successfulLandingsCounter.setText(String.format("%d", successfulLandings));
            failedLandingsCounter.setText(String.format("%d", failedLandings));
        }
    }
}