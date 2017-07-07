package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fedorzaplatin.lunolet.Background;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.Moon;
import com.fedorzaplatin.lunolet.LunarModule;
import com.fedorzaplatin.lunolet.stages.Hud;

import static com.fedorzaplatin.lunolet.Constants.PPM;

public class GameScreen extends BaseScreen{

    private boolean DEBUG = false;

    static private int HEIGHT = Gdx.graphics.getHeight();
    static private int WIDTH = Gdx.graphics.getWidth();

    private World world;
    private Stage stage;
    private Hud hudStage;
    private LunarModule lunarModule;

    private Box2DDebugRenderer b2ddr;

    public GameScreen(final MainClass game) {
        super(game);

        if (DEBUG) {
            b2ddr = new Box2DDebugRenderer(true, true, true, true, true, true);
        }

        stage = new Stage(new FitViewport(WIDTH / PPM, HEIGHT / PPM));
        stage.getCamera().position.set(new Vector2(0, HEIGHT / 2 / PPM), 0);

        world = new World(new Vector2(0, -1.62f), false);
        world.setContactListener(new GameContactListener());

        hudStage = new Hud(new FitViewport(WIDTH, HEIGHT));
    }

    @Override
    public void show() {
        Texture backgroundTexture = new Texture("game-screen/background.png");
        Background background = new Background(backgroundTexture);
        stage.addActor(background);

        Texture lunarModuleTexture = new Texture(Gdx.files.internal("game-screen/challengerTexture.png"), true);
        lunarModule = new LunarModule(world, lunarModuleTexture, new Vector2(0, (HEIGHT * 2 - 30) / PPM));
        stage.addActor(lunarModule);

        Texture moonSurface = new Texture("game-screen/moonTexture.png");
        Moon moon = new Moon(world, moonSurface, new Vector2(-WIDTH / PPM / 2, 0));
        stage.addActor(moon);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!lunarModule.isAlive()) {
            game.setScreen(game.sm.gameOverScreen);
        }

        if (lunarModule.position.y * PPM > 900){
            stage.getCamera().position.set(new float[]{0, 900 / PPM, 0});
        } else if ((400 < lunarModule.position.y * PPM) & (lunarModule.position.y * PPM < 900)){
            stage.getCamera().position.set(new float[]{0, lunarModule.position.y, 0});
        } else {
            stage.getCamera().position.set(new float[]{0, 400 / PPM, 0});
        }

        hudStage.update(lunarModule.getVelocity(), lunarModule.position);
        stage.act();
        world.step(1 / 60f, 6, 2);
        stage.draw();
        hudStage.draw();

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
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        super.dispose();
    }

    public class GameContactListener implements ContactListener {

        private boolean areCollided(Contact contact, Object obj1, Object obj2) {
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            return (userDataA.equals(obj1) && userDataB.equals(obj2)) ||
                    (userDataA.equals(obj2) && userDataB.equals(obj1));
        }

        @Override
        public void beginContact(Contact contact) {
            if (areCollided(contact, "lunar module", "moon")){
                if (lunarModule.getVelocity().len() > 4f){
                    lunarModule.destroy();
                }
            }
        }

        @Override
        public void endContact(Contact contact) {
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }
}
