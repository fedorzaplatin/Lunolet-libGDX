package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.Screen;
import com.fedorzaplatin.lunolet.screens.*;

import java.util.HashMap;
import java.util.Map;

public class ScreensManager {
    final private MainClass game;

    public BaseScreen splashScreen, mainMenu, creditsScreen, gameScreen, gameOverScreen;

    public ScreensManager(final MainClass game){
        this.game = game;
    }

    public void load(){
        splashScreen = new SplashScreen(game);
        mainMenu = new MainMenu(game);
        creditsScreen = new CreditsScreen(game);
        gameScreen = new GameScreen(game);
        gameOverScreen = new GameOverScreen(game);
    }
}
