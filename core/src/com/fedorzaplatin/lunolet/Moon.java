package com.fedorzaplatin.lunolet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

import static com.fedorzaplatin.lunolet.Constants.PPM;

public class Moon extends Actor{
    private World world;
    private Body body;
    private Texture texture;
    private Fixture fixture;
    private PolygonRegion polygonRegion;

    private PolygonSpriteBatch polyBatch;

    public Moon(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;
        this.polyBatch = new PolygonSpriteBatch();
        this.polyBatch.enableBlending();
        position.y = position.y + 100 / PPM;

        float[] vertexes;
        vertexes = generateSurface();

        short[] triangles = new EarClippingTriangulator().computeTriangles(vertexes).toArray();

        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        TextureRegion textureRegion = new TextureRegion(texture);
        this.polygonRegion = new PolygonRegion(textureRegion, vertexes, triangles);
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
        setPosition(body.getPosition().x, body.getPosition().y);
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ((PolygonSpriteBatch)batch).draw(polygonRegion, getX(), getY());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    private float[] generateSurface(){
        float[] vertexes = new float[12 * 2 + 4]; // 16 is number of vertexes
        Random rand = new Random();
        int x = 0;
        float y;
        vertexes[0] = 0;
        vertexes[1] = 0;
        for (int i = 2; i < 24; i += 4) {
            y = rand.nextInt(131) + 20f;
            vertexes[i] = x / PPM;
            vertexes[i + 1] = y / PPM;
            vertexes[i + 2] = (x + 90) / PPM;
            vertexes[i + 3] = y / PPM;
            x += (54 + 90);
        }
        vertexes[26] = 800 / PPM;
        vertexes[27] = 0;
        return vertexes;
    }
}
