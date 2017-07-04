package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.fedorzaplatin.lunolet.screens.MainMenu;

public class MainClass extends Game {
	
	@Override
	public void create () {
		Gdx.graphics.setResizable(false);
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
