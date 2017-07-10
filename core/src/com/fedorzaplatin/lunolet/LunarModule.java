package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LunarModule extends Actor {

    final private float lunarModuleSize = 4.2f;
    private float fuelMass = 3660f;
    final private float lunarModuleMass = 2150f;
    private boolean alive;

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private Vector2 position;
    private Sound engineSound;

    private float angle;

    private InputAdapter inputAdapter;

    public LunarModule(World world, Texture texture, Vector2 position, final Sound engineSound) {
        this.alive = true;
        this.world = world;
        this.texture = texture;
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.engineSound = engineSound;
        this.inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    engineSound.resume();
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    engineSound.pause();
                }
                return true;
            }
        };

        this.position = position;
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(lunarModuleSize / 2, lunarModuleSize / 2);
        fixture = body.createFixture(box, (lunarModuleMass + fuelMass) / (lunarModuleSize * lunarModuleSize));
        fixture.setUserData("lunar module");
        fixture.setFriction(0.4f);
        box.dispose();
        setSize(lunarModuleSize, lunarModuleSize);

        engineSound.loop();
        engineSound.pause();
    }

    public InputAdapter getInputAdapter() {
        return inputAdapter;
    }

    @Override
    public void act(float delta) {
        angle = body.getAngle() * MathUtils.radiansToDegrees + 90f;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            body.applyForceToCenter(new Vector2(0, 29000f).setAngle(angle), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)){
            body.applyForce(new Vector2(0, 29000f).setAngle(angle), body.getWorldPoint(new Vector2(-1f , 0)), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)){
            body.applyForce(new Vector2(0, 29000f).setAngle(angle), body.getWorldPoint(new Vector2(1f, 0)), true);
        }

        position = body.getPosition();
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x, body.getPosition().y);
        batch.draw(texture,
                getX() - getWidth() / 2,
                getY() - getHeight() / 2,
                getWidth() / 2,
                getHeight() / 2,
                getWidth(),
                getHeight(),
                1,
                1,
                body.getAngle() * MathUtils.radiansToDegrees,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false);
    }

    public boolean isAlive(){
        return alive;
    }

    public void destroy(){
        this.alive = false;
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public Vector2 getVelocity(){
        return body.getLinearVelocity();
    }

    public Vector2 getPosition(){
        return position;
    }
}
