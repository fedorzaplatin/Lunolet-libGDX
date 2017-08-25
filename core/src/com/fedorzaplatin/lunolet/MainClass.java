package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fedorzaplatin.lunolet.screens.SplashScreen;

public class MainClass extends Game {

	public ScreensManager sm;
	public AssetManager am;

	private Music mainMenuMusic, gameScreenMusic;
	
	@Override
	public void create () {
		Gdx.graphics.setResizable(false);
		sm = new ScreensManager(this);
		am = new AssetManager();

		am.load("bebas.fnt", BitmapFont.class);

		// Assets of tutorial screen
		am.load("tutorial-screen/tutorial.png", Texture.class);
		am.load("tutorial-screen/1.png", Texture.class);
		am.load("tutorial-screen/2.png", Texture.class);
		am.load("tutorial-screen/3.png", Texture.class);
		am.load("tutorial-screen/controls.png", Texture.class);
		am.load("tutorial-screen/nextBtn.atlas", TextureAtlas.class);

		// Assets of main menu
		am.load("main-menu/background.png", Texture.class);
		am.load("main-menu/startBtn.atlas", TextureAtlas.class);
		am.load("main-menu/tutorialBtn.atlas", TextureAtlas.class);
		am.load("main-menu/creditsBtn.atlas", TextureAtlas.class);
		am.load("main-menu/exitBtn.atlas", TextureAtlas.class);
		am.load("main-menu/mainMenuMusic.mp3", Music.class);

		// Assets of credits screen
		am.load("credits-screen/background.png", Texture.class);
		am.load("credits-screen/backBtn.atlas", TextureAtlas.class);

		// Assets of game screen
		am.load("game-screen/background.png", Texture.class);
		am.load("game-screen/lunarModuleTexture.png", Texture.class);
		am.load("game-screen/fire.atlas", TextureAtlas.class);
		am.load("game-screen/moonTexture.png", Texture.class);
		am.load("game-screen/engineSound.mp3", Sound.class);
		am.load("game-screen/gameScreenMusic.mp3", Music.class);

		// Assets of game over screen
		am.load("game-over-screen/background.png", Texture.class);
		am.load("game-over-screen/againBtn.atlas", TextureAtlas.class);
		am.load("game-over-screen/mainMenuBtn.atlas", TextureAtlas.class);

		//Assets of game completed screen
		am.load("game-completed-screen/background.png", Texture.class);

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
		sm.load();
		gameScreenMusic = am.get("game-screen/gameScreenMusic.mp3");
		gameScreenMusic.setLooping(true);
		mainMenuMusic = am.get("main-menu/mainMenuMusic.mp3");
		mainMenuMusic.setLooping(true);
		mainMenuMusic.play();
		setScreen(sm.mainMenu);
	}

	public void playMainMenuMusic() {
		mainMenuMusic.play();
	}

	public void stopMainMenuMusic() {
		mainMenuMusic.stop();
	}

	public void playGameScreenMusic () {
		gameScreenMusic.play();
	}

	public void stopGameScreenMusic () {
		gameScreenMusic.stop();
	}
}
