package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Shuttle extends Actor {

    final float moduleSize = 4.2f;
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public Vector2 position;

    private float angle;
    private float fuel;
    private boolean alive;

    public Shuttle(World world, Texture texture, Vector2 position) {
        this.alive = true;
        this.world = world;
        this.texture = texture;
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.position = position;
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(moduleSize / 2, moduleSize / 2);
        fixture = body.createFixture(box, 861.6f);
        fixture.setUserData("player");
        fixture.setFriction(0.4f);
        box.dispose();
        setSize(moduleSize, moduleSize);
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

    public Vector2 getVelocity(){
        return body.getLinearVelocity();
    }
}
