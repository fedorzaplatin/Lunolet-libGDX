package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.objects.Background;
import com.fedorzaplatin.lunolet.objects.LunarModule;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.objects.Moon;
import com.fedorzaplatin.lunolet.stages.Hud;
import com.fedorzaplatin.lunolet.stages.PauseMenu;

import static com.fedorzaplatin.lunolet.Constants.PPM;

public class GameScreen extends BaseScreen{

    private boolean DEBUG = false;

    static private int HEIGHT = Gdx.graphics.getHeight();
    static private int WIDTH = Gdx.graphics.getWidth();

    private World world;
    private Stage stage;
    private Hud hudStage;
    private PauseMenu pauseMenuStage;
    private LunarModule lunarModule;
    private Moon moon;
    private Background background;
    private final float worldLeftBorder = 20;
    private final float worldRightBorder = 26;
    private float currentWorldLeftBorder = 0;
    private float currentWorldRightBorder = 0;

    private boolean isLanded;
    private float contactTime;
    private int gameState; // 0 - game, 1 - pause

    private Box2DDebugRenderer b2ddr;

    public GameScreen(final MainClass game) {
        super(game);

        // If debug mode is active create Box2D debug renderer
        if (DEBUG) {
            b2ddr = new Box2DDebugRenderer(true,
                    true,
                    true,
                    true,
                    true,
                    true);
        }

        stage = new Stage(new FitViewport(WIDTH / PPM, HEIGHT / PPM), new PolygonSpriteBatch());
        stage.getCamera().position.set(new Vector2(WIDTH / 2 / PPM, HEIGHT / 2 / PPM), 0);

        pauseMenuStage = new PauseMenu(new FitViewport(WIDTH, HEIGHT),
                (TextureAtlas) game.am.get("buttons.atlas"),
                (BitmapFont) game.am.get("fonts/bebas52.fnt"));
        hudStage = new Hud(new FitViewport(WIDTH, HEIGHT), (BitmapFont) game.am.get("fonts/bebas28.fnt"), -1f);

        world = new World(new Vector2(0, -1.62f), false);
        world.setContactListener(new GameContactListener());

        Texture lunarModuleTexture = (Texture) game.am.get("game-screen/lunarModuleTexture.png");
        TextureAtlas fireSprite = (TextureAtlas) game.am.get("game-screen/fire.atlas");
        TextureAtlas smokeSprite = (TextureAtlas) game.am.get("game-screen/smoke.atlas");
        lunarModule = new LunarModule(world,
                lunarModuleTexture,
                fireSprite,
                smokeSprite,
                new Vector2(25, 1130 / PPM ),
                (Sound) game.am.get("game-screen/engineSound.mp3"));
    }

    @Override
    public void show() {
        isLanded = false;
        contactTime = 0;
        gameState = 0;

        currentWorldLeftBorder = worldLeftBorder;
        currentWorldRightBorder = worldRightBorder;

        background = new Background((Texture) game.am.get("game-screen/background.png"), 45, 60);
        stage.addActor(background);
        
        Texture moonSurface = (Texture) game.am.get("game-screen/moonTexture.png");
        moon = new Moon(world, moonSurface, new Vector2(0, 0));
        stage.addActor(moon);

        lunarModule.createBody();
        stage.addActor(lunarModule);

        Gdx.input.setInputProcessor(new GameScreenInputProcessor());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // If game paused
        if (gameState == 1) {
            switch (pauseMenuStage.getCommand()) {
                case 1:
                    gameState = 0;
                    pauseMenuStage.reset();
                    Gdx.input.setInputProcessor(new GameScreenInputProcessor());
                    break;
                case 2:
                    pauseMenuStage.reset();
                    game.stopGameScreenMusic();
                    game.setScreen(game.sm.mainMenu);
                    break;
                case 3:
                    Gdx.app.exit();
                    break;
            }
        }

        //if lunar module is next to the world's border (left/right) world generates left or right
        if (lunarModule.getPosition().x < currentWorldLeftBorder) {
            moon.generateLeft();
            background.extend(-7);
            currentWorldLeftBorder -= 5f;
        } else if (lunarModule.getPosition().x > currentWorldRightBorder) {
            moon.generateRight();
            background.extend(7);
            currentWorldRightBorder += 5f;
        }

        //check if lunar module has crossed the upper world's border
        if (lunarModule.getPosition().y > (1250 / PPM)) {
            lunarModule.destroy();
        }

        if (!lunarModule.isAlive()) {
            game.setScreen(game.sm.gameOverScreen);
        }

        if (isLanded & lunarModule.getVelocity().len() < 0.01f) {
            contactTime += delta;
        } else {
            contactTime = 0;
        }

        if (isLanded & contactTime > 1) {
            game.setScreen(game.sm.gameCompletedScreen);
        }

        if (lunarModule.getPosition().y * PPM > 900){
            stage.getCamera().position.set(new float[]{lunarModule.getPosition().x, 900 / PPM, 0});
        } else if (lunarModule.getPosition().y < 17){
            stage.getCamera().position.set(new float[]{lunarModule.getPosition().x, 17, 0});
        } else {
            stage.getCamera().position.set(new float[]{lunarModule.getPosition().x, lunarModule.getPosition().y, 0});
        }

        if (gameState == 0) {
            world.step(1 / 60f, 6, 2);
            hudStage.act(lunarModule.getVelocity(), lunarModule.getPosition(), lunarModule.getFuelMass());
            stage.act();
        }

        stage.draw();
        hudStage.draw();
        if (gameState == 1) {
            pauseMenuStage.act();
            pauseMenuStage.draw();
        }

        if (DEBUG) {
            b2ddr.render(world, stage.getCamera().combined);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        stage.clear();
        lunarModule.detach();
        moon.detach();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        super.dispose();
    }

    public void setEffectsVolume(float value) {
        lunarModule.setEffectsVolume(value);
    }

    private class GameContactListener implements ContactListener {

        @Override
        public void beginContact(Contact contact) {
            if (lunarModule.getVelocity().len() > 3f | Math.abs(lunarModule.getAngle() - 90f) >= 13){
                lunarModule.destroy();
            } else {
                isLanded = true;
            }
        }


        @Override
        public void endContact(Contact contact) {
            isLanded = false;
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }

    private class GameScreenInputProcessor implements InputProcessor {
        @Override
        public boolean keyDown(int i) {
            switch (i) {
                case Input.Keys.SPACE:
                    lunarModule.activateMainEngine();
                    break;
                case Input.Keys.A:
                    lunarModule.setActivateAuxiliaryEnginesLeft(true);
                    break;
                case Input.Keys.D:
                    lunarModule.setActivateAuxiliaryEnginesRight(true);
                    break;
                case Input.Keys.ESCAPE:
                    gameState = 1;
                    Gdx.input.setInputProcessor(pauseMenuStage);
                    break;
            }
            return false;
        }

        @Override
        public boolean keyUp(int i) {
            switch(i){
                case Input.Keys.SPACE:
                    lunarModule.deactivateMainEngine();
                    break;
                case Input.Keys.A:
                    lunarModule.setActivateAuxiliaryEnginesLeft(false);
                    break;
                case Input.Keys.D:
                    lunarModule.setActivateAuxiliaryEnginesRight(false);
                    break;
            }
            return false;
        }

        @Override
        public boolean keyTyped(char c) {
            return false;
        }

        @Override
        public boolean touchDown(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchUp(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchDragged(int i, int i1, int i2) {
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1) {
            return false;
        }

        @Override
        public boolean scrolled(int i) {
            return false;
        }
    }
}
