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

public class Moon extends Actor {

    private World world;
    private Body body;
    private Fixture fixture;
    private PolygonRegion polygonRegion;
    private float[] vertices;
    private TextureRegion moonTexture;
    private SurfaceGenerator generator = new SurfaceGenerator();

    public Moon(World world, Texture texture, Vector2 position) {
        this.world = world;
        position.y = position.y + 6; //Lift the surface up a little bit

        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        moonTexture = new TextureRegion(texture);

        vertices = generator.generate();
        short[] triangles = new EarClippingTriangulator().computeTriangles(vertices).toArray();

        //create polygonRegion to fill the moon surface's body
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

    private void update() {
        short[] triangles = new EarClippingTriangulator().computeTriangles(vertices).toArray();
        this.polygonRegion = new PolygonRegion(moonTexture, vertices, triangles);
        ChainShape shape = new ChainShape();
        shape.createChain(vertices);
        body.destroyFixture(fixture);
        fixture = body.createFixture(shape, 0);
        fixture.setUserData("moon");
        fixture.setFriction(0.7f);
        shape.dispose();
    }

    public void generateLeft() {
        vertices = generator.generateLeft(vertices);
        this.update();
    }

    public void generateRight() {
        vertices = generator.generateRight(vertices);
        this.update();
    }

    /**
     * Moon surface generator
     * v.2
     */
    private class SurfaceGenerator{

        private Random rand = new Random();

        final float surfaceMaxHeight = 8f;
        final float surfaceMinHeight = 0f;

        // normal plane length
        final float npMin = 5f;
        final float npMax = 5.5f;

        // small plane length
        final float spMin = 1f;
        final float spMax = 3f;

        // big plane length
        final float bpMin = 5.5f;
        final float bpMax = 6f;

        // interval
        final float intervalMin = 2.7f;
        final float intervalMax = 5f;

        /**
         * Generate initial surface
         * @return array of vertexes (float[]) in format {x1, y1, x2, y2,...}
         */
        public float[] generate() {
            float[] vertices = new float[16 * 2 + 4];
            float x = 0;
            float y;
            float randomLength;

            //Set start vertex
            vertices[0] = 0;
            vertices[1] = -5;

            //Generate horizontal planes
            for (int i = 2; i < 34; i += 4) {
                y = generateRandomY(-1);
                vertices[i] = x;
                vertices[i + 1] = y;

                randomLength = generateRandomLength();
                vertices[i + 2] = x + randomLength;

                if (rand.nextFloat() > 0.6f) {
                    if (rand.nextFloat() < 0.5f) {
                        y -= rand.nextFloat() * 2 + 1;
                    } else {
                        y += rand.nextFloat() * 2 + 1;
                    }
                }
                vertices[i + 3] = y;

                x += randomLength + generateRandomInterval();
            }

            //Set end vertex
            vertices[vertices.length - 2] = x;
            vertices[vertices.length - 1] = -5;
            return vertices;
        }

        /**
         * Generate new surface on the left side of the old surface
         * @param oldVertices vertices of the old surface
         * @return new surface with generated part
         */
        public float[] generateLeft(float[] oldVertices) {
            float[] newVertices = new float[oldVertices.length + 4];
            float randomLength;
            System.arraycopy(oldVertices, 2, newVertices, 6, oldVertices.length - 2);

            float x = oldVertices[2] - generateRandomInterval();
            float y = generateRandomY(oldVertices[3]);
            newVertices[4] = x;
            newVertices[5] = y;

            randomLength = generateRandomLength();
            newVertices[2] = x - randomLength;

            if (rand.nextFloat() > 0.6f) {
                if (rand.nextFloat() < 0.5f) {
                    y -= rand.nextFloat() * 2 + 1;
                } else {
                    y += rand.nextFloat() * 2 + 1;
                }
            }
            newVertices[3] = y;
            newVertices[0] = x - randomLength;
            newVertices[1] = -5;

            return newVertices;
        }

        /**
         * Generate new surface on the right side of the old surface
         * @param oldVertices vertices of the old surface
         * @return new surface with generated part
         */
        public float[] generateRight(float[] oldVertices) {
            float[] newVertices = new float[oldVertices.length + 4];
            System.arraycopy(oldVertices, 0, newVertices, 0, oldVertices.length - 2);
            float x = oldVertices[oldVertices.length - 4] + generateRandomInterval();
            float y = generateRandomY(oldVertices[oldVertices.length - 3]);
            float randomLength;

            newVertices[oldVertices.length - 2] = x;
            newVertices[oldVertices.length - 1] = y;

            randomLength = generateRandomLength();
            newVertices[oldVertices.length] = x + randomLength;

            if (rand.nextFloat() > 0.6f) {
                if (rand.nextFloat() < 0.5f) {
                    y -= rand.nextFloat() * 2 + 1;
                } else {
                    y += rand.nextFloat() * 2 + 1;
                }
            }
            newVertices[oldVertices.length + 1] = y;

            newVertices[oldVertices.length + 2] = x + randomLength;
            newVertices[oldVertices.length + 3] = -5;

            return newVertices;
        }

        /**
         * Generate random value which is length of a horizontal plane
         * @return generated length in meters
         */
        private float generateRandomLength() {
            float length;

            if (rand.nextFloat() > 0.35f) {
                if (rand.nextFloat() > 0.35f) {
                    length = rand.nextFloat() * (spMax - spMin) + spMin;
                } else {
                    length = rand.nextFloat() * (bpMax - bpMin) + bpMin;
                }
            } else {
                length = rand.nextFloat() * (npMax - npMin) + npMin;
            }

            return length;
        }

        /**
         * Generate random value which is height of a plane
         * @param previousY y coordinate of the previous vertex
         * @return generated coordinate in meters
         */
        private float generateRandomY(float previousY) {
            float y = rand.nextFloat() * (surfaceMaxHeight - surfaceMinHeight) + surfaceMinHeight;
            while (Math.abs(previousY - y) < 0.3f){
                y = rand.nextFloat() * (surfaceMaxHeight - surfaceMinHeight) + surfaceMinHeight;
            }
            return y;
        }

        /**
         * Generate random value which is length of a interval between two horizontal planes
         * @return generated length in meters
         */
        private float generateRandomInterval() {
            return rand.nextFloat() * (intervalMax - intervalMin) + intervalMin;
        }
    }
}