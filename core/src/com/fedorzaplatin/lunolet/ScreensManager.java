package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.Screen;
import com.fedorzaplatin.lunolet.screens.*;

import java.util.HashMap;
import java.util.Map;

public class ScreensManager {
    private Map<String, BaseScreen> screens;

    final MainClass game;

    public ScreensManager(MainClass game){
        this.game = game;
        screens = new HashMap<String, BaseScreen>();
    }

    public void load(){
        screens.put("splash", new SplashScreen(game));
        screens.put("main menu", new MainMenu(game));
        screens.put("credits", new CreditsScreen(game));
        screens.put("game", new GameScreen(game));
    }

    public Screen get(String screenName){
        return this.screens.get(screenName);
    }
}
