package com.fedorzaplatin.lunolet.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;
import com.fedorzaplatin.lunolet.ui.LunoletSliderStyle;
import org.ini4j.Ini;

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
    public SettingsScreen(MainClass game, Ini config) {
        super(game);
        this.stage = new Stage();
        this.changed = false;

        Ini.Section section = config.get("SETTINGS");
        this.currentMusicVolume = Float.parseFloat(section.get("musicVolume"));
        this.currentEffectsVolume = Float.parseFloat(section.get("effectsVolume"));

        Image background = new Image((Texture) game.am.get("settings-screen/background.png"));

        Slider.SliderStyle sliderStyle;
        sliderStyle = new LunoletSliderStyle((TextureAtlas) game.am.get("settings-screen/slider.atlas"));
        musicVolumeSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        musicVolumeSlider.setValue(currentMusicVolume);

        sliderStyle = new LunoletSliderStyle((TextureAtlas) game.am.get("settings-screen/slider.atlas"));
        effectsVolumeSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        effectsVolumeSlider.setValue(currentEffectsVolume);

        ImageButton.ImageButtonStyle buttonStyle;
        buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "apply");
        applyBtn = new ImageButton(buttonStyle);

        buttonStyle = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "back");
        backBtn = new ImageButton(buttonStyle);

        Table table = new Table();
        table.setPosition(0, -120);
        table.setFillParent(true);
        table.add(musicVolumeSlider).right().padBottom(54).padRight(34).expandX();
        table.row();
        table.add(effectsVolumeSlider).right().padTop(54).padBottom(67).padRight(34).expandX();
        table.row();
        table.add(backBtn).bottom().center().padTop(67).padBottom(43).expandX();

        stage.addActor(background);
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

        if ((currentMusicVolume != musicVolumeSlider.getValue() | currentEffectsVolume != effectsVolumeSlider.getValue()) & !changed) {
            changed = !changed;
        } else if ((currentMusicVolume == musicVolumeSlider.getValue() | currentEffectsVolume == effectsVolumeSlider.getValue()) & changed) {
            changed = !changed;
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
