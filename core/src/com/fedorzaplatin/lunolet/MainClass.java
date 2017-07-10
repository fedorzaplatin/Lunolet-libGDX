package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.fedorzaplatin.lunolet.screens.SplashScreen;

public class MainClass extends Game {

	public ScreensManager sm;
	public AssetManager am;
	
	@Override
	public void create () {
		Gdx.graphics.setResizable(false);
		sm = new ScreensManager(this);
		am = new AssetManager();

		am.load("bebas.fnt", BitmapFont.class);

		// Assets of main menu
		am.load("main-menu/background.png", Texture.class);
		am.load("main-menu/startBtn.atlas", TextureAtlas.class);
		am.load("main-menu/creditsBtn.atlas", TextureAtlas.class);
		am.load("main-menu/exitBtn.atlas", TextureAtlas.class);

		// Assets of credits screen
		am.load("credits-screen/background.png", Texture.class);
		am.load("credits-screen/backBtn.atlas", TextureAtlas.class);

		// Assets of game screen
		am.load("game-screen/background.png", Texture.class);
		am.load("game-screen/challengerTexture.png", Texture.class);
		am.load("game-screen/moonTexture.png", Texture.class);
		am.load("game-screen/engineSound.mp3", Sound.class);

		// Assets of game over screen
		am.load("game-over-screen/background.png", Texture.class);
		am.load("game-over-screen/againBtn.atlas", TextureAtlas.class);
		am.load("game-over-screen/mainMenuBtn.atlas", TextureAtlas.class);

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
		setScreen(sm.mainMenu);
	}
}
