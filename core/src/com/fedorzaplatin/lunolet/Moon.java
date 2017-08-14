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
    private Fixture fixture;
    private PolygonRegion polygonRegion;
    private float[] vertices;
    private TextureRegion moonTexture;

    public Moon(World world, Texture texture, Vector2 position) {
        this.world = world;
        position.y = position.y + 100 / PPM; //Lift the surface a little bit

        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        moonTexture = new TextureRegion(texture);

        vertices = generateSurface();
        short[] triangles = new EarClippingTriangulator().computeTriangles(vertices).toArray();

        this.polygonRegion = new PolygonRegion(moonTexture, vertices, triangles);
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);
        ChainShape shape = new ChainShape();
        shape.createChain(vertices);
        fixture = body.createFixture(shape, 0);
        fixture.setUserData("moon");
        fixture.setFriction(0.7f);
        shape.dispose();

        //Set the actor's position to draw the texture
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
        float[] vertexes = new float[12 * 2 + 4];
        Random rand = new Random();
        int x = 0;
        float y;

        //Set start vertex
        vertexes[0] = 0;
        vertexes[1] = 0;

        //Generate 6 horizontal planes
        for (int i = 2; i < 24; i += 4) {
            y = rand.nextInt(131) + 20f;
            vertexes[i] = x / PPM;
            vertexes[i + 1] = y / PPM;
            vertexes[i + 2] = (x + 90) / PPM;
            vertexes[i + 3] = y / PPM;
            x += 54 + 90;
        }

        //Set end vertex
        vertexes[26] = 800 / PPM;
        vertexes[27] = 0;
        return vertexes;
    }

    public void generateLeft() {
        Random rand = new Random();
        float[] newVertices = new float[vertices.length + 4];
        System.arraycopy(vertices, 2, newVertices, 6, vertices.length - 2);
        float x = vertices[2] * PPM - 54;
        float y = rand.nextInt(131) + 20f;

        newVertices[4] = x / PPM;
        newVertices[5] = y / PPM;
        newVertices[2] = (x - 90) / PPM;
        newVertices[3] = y / PPM;
        newVertices[0] = (x - 90) / PPM;
        newVertices[1] = 0;

        vertices = newVertices;
        this.update();
    }

    public void generateRight() {
        Random rand = new Random();
        float[] newVertices = new float[vertices.length + 4];
        System.arraycopy(vertices, 0, newVertices, 0, vertices.length - 2);
        float x = vertices[vertices.length - 3] * PPM + 54;
        float y = rand.nextInt(131) + 20f;

        newVertices[vertices.length - 1] = x / PPM;
        newVertices[vertices.length] = y / PPM;
        newVertices[vertices.length + 1] = (x + 90) / PPM;
        newVertices[vertices.length + 2] = y / PPM;
        newVertices[vertices.length + 3] = (x + 90) / PPM;
        newVertices[vertices.length + 4] = 0;

        vertices = newVertices;
        this.update();
    }

    private void update() {
        short[] triangles = new EarClippingTriangulator().computeTriangles(vertices).toArray();
        this.polygonRegion = new PolygonRegion(moonTexture, vertices, triangles);
        ChainShape shape = new ChainShape();
        shape.createChain(vertices);
        fixture = body.createFixture(shape, 0);
        fixture.setUserData("moon");
        fixture.setFriction(0.7f);
        shape.dispose();
    }
}