package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fedorzaplatin.lunolet.screens.GameScreen;
import com.fedorzaplatin.lunolet.screens.MainMenu;
import com.fedorzaplatin.lunolet.screens.SplashScreen;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public class MainClass extends Game {

	public ScreensManager sm;
	public AssetManager am;
	private Ini config;

	private Music gameScreenMusic;
	
	@Override
	public void create () {
		Gdx.graphics.setResizable(false);

		try {
			config = new Ini(new File("config.ini"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		sm = new ScreensManager(this);
		am = new AssetManager();

		am.load("buttons.atlas", TextureAtlas.class);
		am.load("fonts/courierNew30.fnt", BitmapFont.class);
		am.load("fonts/bebas52.fnt", BitmapFont.class);
		am.load("fonts/bebas28.fnt", BitmapFont.class);
		am.load("fonts/bebas63.fnt", BitmapFont.class);

		// Assets of tutorial screen
		am.load("tutorial-screen/tutorialScreenTextures.atlas", TextureAtlas.class);

		// Assets of main menu
		am.load("main-menu/background.png", Texture.class);
		am.load("main-menu/mainMenuMusic.mp3", Music.class);

		// Assets of settings screen
		am.load("settings-screen/slider.atlas", TextureAtlas.class);

		// Assets of game screen
		am.load("game-screen/background.png", Texture.class);
		am.load("game-screen/lunarModuleTexture.png", Texture.class);
		am.load("game-screen/fire.atlas", TextureAtlas.class);
		am.load("game-screen/smoke.atlas", TextureAtlas.class);
		am.load("game-screen/moonTexture.png", Texture.class);
		am.load("game-screen/engineSound.mp3", Sound.class);
		am.load("game-screen/gameScreenMusic.mp3", Music.class);

		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		am.dispose();
		super.dispose();
	}

	public void finishLoad() {
		sm.load(config);

		gameScreenMusic = am.get("game-screen/gameScreenMusic.mp3");
		gameScreenMusic.setLooping(true);

		Ini.Section section = config.get("SETTINGS");
        float musicVolume = Float.parseFloat(section.get("musicVolume"));
        float effectsVolume = Float.parseFloat(section.get("effectsVolume"));

        this.setMusicVolume(musicVolume);
        this.setEffectsVolume(effectsVolume);

		setScreen(sm.mainMenu);
	}

	public void playGameScreenMusic () {
		gameScreenMusic.play();
	}

	public void stopGameScreenMusic () {
		gameScreenMusic.stop();
	}

	public void setMusicVolume(float value) {
		((MainMenu) sm.mainMenu).setMusicVolume(value);
		gameScreenMusic.setVolume(value);
	}

	public void setEffectsVolume(float value) {
		((GameScreen) sm.gameScreen).setEffectsVolume(value);
	}

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
}
