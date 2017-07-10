package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.fedorzaplatin.lunolet.Constants.PPM;

public class Moon extends Actor{
    private World world;
    private Body body;
    private Texture texture;
    private Fixture fixture;

    public Moon(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;

        Vector2[] vertexes = {new Vector2(-1 / PPM, 229 / PPM),
                new Vector2(97 / PPM, 229 / PPM),
                new Vector2(147 / PPM, 184 / PPM),
                new Vector2(299 / PPM, 184 / PPM),
                new Vector2(349 / PPM, 163 / PPM),
                new Vector2(453 / PPM, 163 / PPM),
                new Vector2(489 / PPM, 184 / PPM),
                new Vector2(516 / PPM, 184 / PPM),
                new Vector2(539 / PPM, 232 / PPM),
                new Vector2(627 / PPM, 232 / PPM),
                new Vector2(670 / PPM, 195 / PPM),
                new Vector2(712 / PPM, 195 / PPM),
                new Vector2(728 / PPM, 218 / PPM),
                new Vector2(801 / PPM, 218 / PPM)};

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);
        ChainShape shape = new ChainShape();
        shape.createChain(vertexes);
        fixture = body.createFixture(shape, 0);
        fixture.setUserData("moon");
        fixture.setFriction(0.4f);
        shape.dispose();

        setSize(800f / PPM, 600f / PPM);
        setPosition(body.getPosition().x, body.getPosition().y);
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
