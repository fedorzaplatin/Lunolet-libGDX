package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MainClass extends Game {

	public ScreensManager sm;
	
	@Override
	public void create () {
		Gdx.graphics.setResizable(false);
		sm = new ScreensManager(this);
		sm.load();
		this.setScreen(sm.splashScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
