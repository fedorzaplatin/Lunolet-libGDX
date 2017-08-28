package com.fedorzaplatin.lunolet;

import com.fedorzaplatin.lunolet.screens.*;
import org.ini4j.Ini;

public class ScreensManager {
    final private MainClass game;

    public BaseScreen splashScreen, mainMenu, creditsScreen, tutorialScreen, gameScreen, gameOverScreen, gameCompletedScreen, settingsScreen;

    public ScreensManager(final MainClass game){
        this.game = game;
    }

    public void load(Ini config){
        splashScreen = new SplashScreen(game);
        mainMenu = new MainMenu(game);
        creditsScreen = new CreditsScreen(game);
        tutorialScreen = new TutorialScreen(game);
        settingsScreen = new SettingsScreen(game, config);
        gameScreen = new GameScreen(game);
        gameOverScreen = new GameOverScreen(game);
        gameCompletedScreen = new GameCompletedScreen(game);
    }
}
