package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LunarModule extends Actor {

    //variables related to the lunar module
    final private float lunarModuleWidth = 4.2f;
    private float lunarModuleHeight = 0;
    final private float lunarModuleMass = 2150f;
    private float fuelMass = 400f;
    private MassData massData;
    private boolean alive;
    private boolean activateEngine = false;
    private Texture lunarModuleTexture;
    private Body body;
    private Fixture fixture;
    private Sound engineSound;
    private float angle;

    //variables related to the main engine's fire sprite
    private float fireSpriteWidth = 0.6f;
    private float fireSpriteHeight = fireSpriteWidth * 162 / 131;
    private Animation fireAnimation;

    private float time;
    private World world;

    public LunarModule(World world, Texture texture, TextureAtlas fireSprite, Vector2 position, final Sound engineSound) {
        this.alive = true;
        this.world = world;
        this.lunarModuleTexture = texture;

        lunarModuleHeight = lunarModuleWidth * texture.getHeight() / texture.getWidth();
        
        this.lunarModuleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.engineSound = engineSound;

        //Create box body
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

        fireAnimation = new Animation<TextureRegion>(0.04f, fireSprite.findRegions("frame"), Animation.PlayMode.LOOP);
        time = 0;

        //Star and pause the engine's sound to resume it when space key is pressed
        engineSound.loop();
        engineSound.pause();
    }

    @Override
    public void act(float delta) {
        time += delta;

        //get an angle which the lunar module is tilted relative to a perpendicular to the moon surface
        angle = body.getAngle() * MathUtils.radiansToDegrees + 90f;

        if (activateEngine) {
            body.applyForceToCenter(new Vector2(0, 9000f).setAngle(angle), true);
            massData = body.getMassData();
            massData.mass -= 0.03f;
            body.setMassData(massData);
            fuelMass -= 0.03f;
        }
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        //Set the actor's position to draw lunar module's lunarModuleTexture according to the body's position
        setPosition(body.getPosition().x, body.getPosition().y);
        System.out.println(body.getPosition().x);

        //Draw the main engine's fire
        float fireSpriteOriginX = fireSpriteWidth / 2;
        float fireSpriteOriginY = fireSpriteHeight / 2 + 1.65f;
        if (activateEngine) {
            batch.draw((TextureRegion) fireAnimation.getKeyFrame(time, true),
                    getX() - fireSpriteOriginX,
                    getY() - fireSpriteOriginY,
                    fireSpriteOriginX,
                    fireSpriteOriginY,
                    fireSpriteWidth,
                    fireSpriteHeight,
                    1,
                    1,
                    body.getAngle() * MathUtils.radiansToDegrees);
        }

        //Draw thr lunar module's texture
        batch.draw(lunarModuleTexture,
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
                lunarModuleTexture.getWidth(),
                lunarModuleTexture.getHeight(),
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
        return body.getPosition();
    }

    public float getAngle() {
        return angle;
    }

    public float getFuelMass() {
        return fuelMass;
    }

    public void activateMainEngine() {
        if (fuelMass > 0) {
            this.activateEngine = true;
            engineSound.play();
        }
    }

    public void deactivateMainEngine() {
        if (this.activateEngine) {
            this.activateEngine = false;
            engineSound.pause();
        }
    }

    public void rotateLeft() {
        body.applyForce(new Vector2(0, 15000f).setAngle(angle), body.getWorldPoint(new Vector2(2.0f, 3.2f)), true);
        body.applyForce(new Vector2(0, 15000f).setAngle(-angle), body.getWorldPoint(new Vector2(-2.0f, 3.2f)), true);
    }

    public void rotateRight() {
        body.applyForce(new Vector2(0, 17000f).setAngle(angle), body.getWorldPoint(new Vector2(-2.0f , 3.2f)), true);
        body.applyForce(new Vector2(0, 17000f).setAngle(-angle), body.getWorldPoint(new Vector2(2.0f, 3.2f)), true);
    }
}