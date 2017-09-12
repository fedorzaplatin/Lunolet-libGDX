package com.fedorzaplatin.lunolet.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;
import com.fedorzaplatin.lunolet.ui.LunoletSliderStyle;
import org.ini4j.Ini;

import java.io.IOException;

public class SettingsScreen extends BaseScreen {

    private Stage stage;
    private ImageButton applyBtn, backBtn;
    private Slider musicVolumeSlider, effectsVolumeSlider;
    private float currentMusicVolume, currentEffectsVolume;
    private boolean changed;

    /**
     * Constructor
     * @param game main class that extends class Game
     */
    public SettingsScreen(final MainClass game, final Ini config) {
        super(game);
        this.stage = new Stage();
        this.changed = false;

        Ini.Section section = config.get("SETTINGS");
        this.currentMusicVolume = Float.parseFloat(section.get("musicVolume"));
        this.currentEffectsVolume = Float.parseFloat(section.get("effectsVolume"));

        Label.LabelStyle labelStyle;
        labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas52.fnt"), Color.WHITE);
        Label settingsLabel = new Label("settings", labelStyle);

        labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/courierNew30.fnt"), Color.WHITE);
        Label musicVolume = new Label("Music volume", labelStyle);

        Slider.SliderStyle sliderStyle;
        sliderStyle = new LunoletSliderStyle((TextureAtlas) game.am.get("settings-screen/slider.atlas"));
        musicVolumeSlider = new Slider(0, 1, 0.01f, false, sliderStyle);

        Label effectsVolume = new Label("Effects volume", labelStyle);

        sliderStyle = new LunoletSliderStyle((TextureAtlas) game.am.get("settings-screen/slider.atlas"));
        effectsVolumeSlider = new Slider(0, 1, 0.01f, false, sliderStyle);

        ImageButton.ImageButtonStyle buttonStyle;
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

        buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "back");
        backBtn = new ImageButton(buttonStyle);
        backBtn.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.sm.mainMenu);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center().top();
        table.add(settingsLabel).pad(26).expandX().center().top().colspan(2);
        table.row().expand();
        table.add(musicVolume).pad(46).left();
        table.add(musicVolumeSlider).center();
        table.row().expand();
        table.add(effectsVolume).pad(46).left();
        table.add(effectsVolumeSlider).center();
        table.row().expand();
        table.add(backBtn).pad(23).left().bottom();
        table.add(applyBtn).pad(23).right().bottom();

        //stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        musicVolumeSlider.setValue(currentMusicVolume);
        effectsVolumeSlider.setValue(currentEffectsVolume);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(3 / 255, 4 / 255,3 / 255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
}
