package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LunarModule extends Actor {

    final private float lunarModuleWidth = 4.2f;
    final private float lunarModuleHeight = 4.2f * 714 / 643;
    private float fuelMass = 3660f;
    final private float lunarModuleMass = 2150f;
    private MassData massData;
    private boolean alive;
    private boolean activateEngine = false;

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private Vector2 position;
    private Sound engineSound;

    private float angle;

    public LunarModule(World world, Texture texture, Vector2 position, final Sound engineSound) {
        this.alive = true;
        this.world = world;
        this.texture = texture;
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.engineSound = engineSound;

        this.position = position;
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(lunarModuleWidth / 2, lunarModuleHeight / 2);
        fixture = body.createFixture(box, (lunarModuleMass + fuelMass) / (lunarModuleWidth * lunarModuleHeight));
        fixture.setUserData("lunar module");
        fixture.setFriction(0.4f);
        box.dispose();
        setSize(lunarModuleWidth, lunarModuleHeight);

        massData = body.getMassData();
        engineSound.loop();
        engineSound.pause();
    }

    @Override
    public void act(float delta) {
        angle = body.getAngle() * MathUtils.radiansToDegrees + 90f;

        if (activateEngine & fuelMass > 0) {
            body.applyForceToCenter(new Vector2(0, 17000f).setAngle(angle), true);
            massData = body.getMassData();
            massData.mass -= 5f;
            body.setMassData(massData);
            fuelMass -= 5f;
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

    public boolean isAlive() {
        return alive;
    }

    public void destroy() {
        this.alive = false;
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
        engineSound.stop();
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getAngle() {
        return angle;
    }

    public float getFuelMass() {
        return fuelMass;
    }

    public void activateMainEngine() {
        this.activateEngine = true;
        engineSound.play();
    }

    public void deactivateMainEngine() {
        this.activateEngine = false;
        engineSound.pause();
    }

    public void rotateLeft() {
        body.applyForce(new Vector2(0, 29000f).setAngle(angle), body.getWorldPoint(new Vector2(1f, 0)), true);
    }

    public void rotateRight() {
        body.applyForce(new Vector2(0, 29000f).setAngle(angle), body.getWorldPoint(new Vector2(-1f , 0)), true);
    }
}