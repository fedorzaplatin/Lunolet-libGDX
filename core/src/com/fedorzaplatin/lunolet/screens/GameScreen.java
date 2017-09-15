package com.fedorzaplatin.lunolet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fedorzaplatin.lunolet.objects.Background;
import com.fedorzaplatin.lunolet.objects.LunarModule;
import com.fedorzaplatin.lunolet.MainClass;
import com.fedorzaplatin.lunolet.objects.Moon;
import com.fedorzaplatin.lunolet.ui.LunoletButtonsStyle;

import static com.fedorzaplatin.lunolet.Constants.PPM;

public class GameScreen extends BaseScreen {

    private boolean DEBUG = false;

    static private int HEIGHT = Gdx.graphics.getHeight();
    static private int WIDTH = Gdx.graphics.getWidth();

    private Stage mainStage, gameCompleted, gameOver;
    private Hud hudStage;
    private PauseMenu pauseMenuStage;
    private LunarModule lunarModule;
    private World world;

    private int state;
    private Music music;
    private InputProcessor gameInputProcessor;

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

        gameInputProcessor = new GameScreenInputProcessor();

        mainStage = new MainStage(new FitViewport(WIDTH / PPM, HEIGHT / PPM), new PolygonSpriteBatch());
        lunarModule = ((MainStage) mainStage).getLunarModule();
        world = ((MainStage) mainStage).getWorld();

        pauseMenuStage = new PauseMenu(new FitViewport(WIDTH, HEIGHT),
                (TextureAtlas) game.am.get("buttons.atlas"),
                (BitmapFont) game.am.get("fonts/bebas52.fnt"));
        hudStage = new Hud(new FitViewport(WIDTH, HEIGHT), (BitmapFont) game.am.get("fonts/bebas28.fnt"));
        gameOver = new GameOver(new FitViewport(WIDTH, HEIGHT));
        gameCompleted = new GameCompleted(new FitViewport(WIDTH, HEIGHT));

        music = game.am.get("game-screen/gameScreenMusic.mp3");
        music.setLooping(true);
    }

    @Override
    public void show() {
        updateScreen(0);
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (state == 0) {
            mainStage.act(delta);
            hudStage.act(lunarModule.getVelocity(), lunarModule.getPosition(), lunarModule.getFuelMass());
            mainStage.draw();
            hudStage.draw();
        } else if (state == 1) {
            pauseMenuStage.act();
            mainStage.draw();
            hudStage.draw();
            pauseMenuStage.draw();
        } else if (state == 2) {
            gameCompleted.act();
            gameCompleted.draw();
        } else if (state == 3) {
            gameOver.act();
            gameOver.draw();
        }

        if (DEBUG) {
            b2ddr.render(world, mainStage.getCamera().combined);
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
        music.stop();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        music.dispose();
        super.dispose();
    }

    public void setEffectsVolume(float value) {
        lunarModule.setEffectsVolume(value);
    }

    public void setMusicVolume(float value) {
        music.setVolume(value);
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
                    updateScreen(1);
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

    /**
     * @param stateNumber 0 - game, 1 - pause, 2 - game completed, 3 - game over, 4 - switch to the main menu, 5 - exit the game
     */
    private void updateScreen(int stateNumber) {
        switch (stateNumber) {
            case 0:
                if (state != 1) {
                    ((MainStage) mainStage).reset();
                    lunarModule = ((MainStage) mainStage).getLunarModule();
                }
                state = 0;
                Gdx.input.setInputProcessor(gameInputProcessor);
                break;
            case 1:
                state = 1;
                Gdx.input.setInputProcessor(pauseMenuStage);
                break;
            case 2:
                ((MainStage) mainStage).hide();
                Gdx.input.setInputProcessor(gameCompleted);
                state = 2;
                break;
            case 3:
                ((MainStage) mainStage).hide();
                Gdx.input.setInputProcessor(gameOver);
                state = 3;
                break;
            case 4:
                if (state == 1) {
                    ((MainStage) mainStage).hide();
                }
                state = 4;
                game.setScreen(game.sm.mainMenu);
                break;
            case 5:
                Gdx.app.exit();
                break;
        }
    }

    /**
     * Game stage
     */
    private class MainStage extends Stage {

        private World world;
        private LunarModule lunarModule;
        private Moon moon;
        private Background background;
        private final float worldLeftBorder = 20;
        private final float worldRightBorder = 26;
        private float currentWorldLeftBorder = 0;
        private float currentWorldRightBorder = 0;

        private boolean isLanded;
        private float contactTime;

        /**
         * Constructor
         * @param viewport
         * @param batch ATTENTION. The main stage required PolygonSpriteBatch. You have to pass PolygonSpriteBatch on construct
         */
        public MainStage(Viewport viewport, Batch batch) {
            super(viewport, batch);

            // Set position of the camera
            getCamera().position.set(new Vector2(WIDTH / 2 / PPM, HEIGHT / 2 / PPM), 0);

            // Create Box2D world
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

        /**
         * Reset game world to the initial state
         */
        public void reset() {
            isLanded = false;
            contactTime = 0;

            currentWorldLeftBorder = worldLeftBorder;
            currentWorldRightBorder = worldRightBorder;

            background = new Background((Texture) game.am.get("game-screen/background.png"), 45, 60);
            addActor(background);

            Texture moonSurface = (Texture) game.am.get("game-screen/moonTexture.png");
            moon = new Moon(world, moonSurface, new Vector2(0, 0));
            addActor(moon);

            lunarModule.createBody();
            addActor(lunarModule);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            // if the lunar module is next to the world's border (left/right) world generates left or right
            if (lunarModule.getPosition().x < currentWorldLeftBorder) {
                moon.generateLeft();
                background.extend(-7);
                currentWorldLeftBorder -= 5f;
            } else if (lunarModule.getPosition().x > currentWorldRightBorder) {
                moon.generateRight();
                background.extend(7);
                currentWorldRightBorder += 5f;
            }

            // check if the lunar module has crossed the upper world's border
            if (lunarModule.getPosition().y > (1250 / PPM)) {
                lunarModule.destroy();
            }

            // if the lunar module is not alive canceling the game
            if (!lunarModule.isAlive()) {
                updateScreen(3);
            }

            if (isLanded & lunarModule.getVelocity().len() < 0.01f) {
                contactTime += delta;
            } else {
                contactTime = 0;
            }

            if (isLanded & contactTime > 1) {
                updateScreen(2);
            }

            if (lunarModule.getPosition().y * PPM > 900){
                getCamera().position.set(new float[]{lunarModule.getPosition().x, 900 / PPM, 0});
            } else if (lunarModule.getPosition().y < 17){
                getCamera().position.set(new float[]{lunarModule.getPosition().x, 17, 0});
            } else {
                getCamera().position.set(new float[]{lunarModule.getPosition().x, lunarModule.getPosition().y, 0});
            }

            world.step(1 / 60f, 6, 2);
        }

        public void hide() {
            moon.detach();
            lunarModule.detach();
            clear();
        }

        public LunarModule getLunarModule() {
            return lunarModule;
        }

        public World getWorld() {
            return world;
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
    }

    /**
     * Game over screen
     */
    private class GameOver extends Stage {
        public GameOver(Viewport viewport) {
            super(viewport);

            // Create text label
            Label.LabelStyle labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas52.fnt"), Color.WHITE);
            Label textLabel = new Label("game over", labelStyle);
            textLabel.setAlignment(Align.center);

            // Create again button
            ImageButton.ImageButtonStyle style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "again");
            ImageButton againBtn = new ImageButton(style);
            againBtn.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateScreen(0);
                }
            });

            // Create main menu button
            style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "mainMenu");
            ImageButton mainMenuBtn = new ImageButton(style);
            mainMenuBtn.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateScreen(4);
                }
            });

            // Create table
            Table table = new Table();
            table.setFillParent(true);
            table.center();
            table.add(textLabel).colspan(2);
            table.row();
            table.add(mainMenuBtn).pad(23);
            table.add(againBtn).pad(23);

            // Add table to the stage
            addActor(table);
        }
    }

    /**
     * Game completed screen
     */
    private class GameCompleted extends Stage {
        public GameCompleted(Viewport viewport) {
            super(viewport);

            // Create text label
            Label.LabelStyle labelStyle = new Label.LabelStyle((BitmapFont) game.am.get("fonts/bebas52.fnt"), Color.WHITE);
            Label textLabel = new Label("congratulations!\nlanding   is   successful", labelStyle);
            textLabel.setAlignment(Align.center);

            // Create again button
            ImageButton.ImageButtonStyle style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "again");
            ImageButton againBtn = new ImageButton(style);
            againBtn.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateScreen(0);
                }
            });

            // Create main menu button
            style = new LunoletButtonsStyle((TextureAtlas) game.am.get("buttons.atlas"), "mainMenu");
            ImageButton mainMenuBtn = new ImageButton(style);
            mainMenuBtn.addCaptureListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateScreen(4);
                }
            });

            // Create table
            Table table = new Table();
            table.setFillParent(true);
            table.center();
            table.add(textLabel).colspan(2);
            table.row();
            table.add(mainMenuBtn).pad(23);
            table.add(againBtn).pad(23);

            // Add table to the stage
            addActor(table);
        }
    }

    /**
     * Pause menu
     */
    private class PauseMenu extends Stage {
        private int command; // 0 - keep pause, 1 - resume game, 2 - main menu, 3 - exit

        /**
         * Constructor
         * @param viewport
         * @param buttons TextureAtlas that contains buttons textures (resume, mainMenu, exit)
         * @param font BitmapFont which will be used to create heading label
         */
        public PauseMenu(Viewport viewport, TextureAtlas buttons, BitmapFont font) {
            super(viewport);
            this.command = 0;

            // Create heading label
            Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
            Label headingLabel = new Label("pause", labelStyle);

            // Create resume button
            ImageButton.ImageButtonStyle style;
            style = new LunoletButtonsStyle(buttons, "resume");
            ImageButton resumeBtn = new ImageButton(style);
            resumeBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    updateScreen(0);
                }
            });

            // Create main menu button
            style = new LunoletButtonsStyle(buttons, "mainMenu");
            ImageButton mainMenuBtn = new ImageButton(style);
            mainMenuBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    updateScreen(4);
                }
            });

            // Create exit button
            style = new LunoletButtonsStyle(buttons, "exit");
            ImageButton exitBtn = new ImageButton(style);
            exitBtn.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    updateScreen(5);
                }
            });

            // Create layout table
            Table table = new Table();
            table.setFillParent(true);
            table.add(headingLabel).top().pad(26);
            table.row();
            table.add(resumeBtn).pad(8);
            table.row();
            table.add(mainMenuBtn).pad(8);
            table.row();
            table.add(exitBtn).pad(8);

            addActor(table);
        }

        @Override
        public boolean keyDown(int keyCode) {
            boolean result = super.keyDown(keyCode);

            // If Escape key pressed switch the command to 'resume' (1)
            if (keyCode == Input.Keys.ESCAPE) {
                updateScreen(0);
                return true;
            }

            return result;
        }

        /**
         * @return Number of command which game screen have to execute. 0 - keep pause menu, 1 - resume game, 2 - switch to main menu, 3 - exit the game
         */
        public int getCommand() {
            return command;
        }


        public void reset() {
            command = 0;
        }
    }

    /**
     * HUD
     */
    private class Hud extends Stage {
        private Label velocity;
        private Label altitude;
        private Label fuelMass;
        private float lunarModuleHeight;


        /**
         * Game screen's HUD
         * @param viewport
         * @param font BitmapFont which wil be used to create labels
         */
        public Hud(Viewport viewport, BitmapFont font) {
            super(viewport);

            this.lunarModuleHeight = lunarModuleHeight;

            // Create labels
            Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
            velocity = new Label("0", style);
            altitude = new Label("0", style);
            fuelMass = new Label("0", style);

            // Create table
            Table table = new Table();
            table.left().top();
            table.pad(10);
            table.setFillParent(true);
            table.add(velocity).align(Align.left).pad(2);
            table.row();
            table.add(altitude).align(Align.left).pad(4);
            table.row();
            table.add(fuelMass).align(Align.left).pad(4);

            addActor(table);
        }

        public void act(Vector2 velocity, Vector2 position, float fuel) {
            this.velocity.setText(String.format("Velocity:  %02.2f   m/s", velocity.len()));
            this.altitude.setText(String.format("Alt.:  %03.0f   m", position.y));
            this.fuelMass.setText(String.format("Fuel:  %04.0f   kg", fuel));
            super.act();
        }
    }
}