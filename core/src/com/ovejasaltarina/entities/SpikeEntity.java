package com.ovejasaltarina.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.ovejasaltarina.utils.Constants.PIXELS_IN_METERS;

/**
 * Created by andrearodriguez on 1/13/17.
 */

public class SpikeEntity extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public SpikeEntity(Texture texture, World world, float x, float y) {
        this.texture = texture;
        this.world = world;

        BodyDef def = new BodyDef();                // (1) Give it some definition.
        def.position.set(x, y + 0.5f);              // (2) Position the body on the world.
        body = world.createBody(def);               // (3) Create the body.

        // Now give it a shape.
        PolygonShape box = new PolygonShape();      // (1) We will make a polygon.
        Vector2[] vertices = new Vector2[4];        // (2) However vertices will be manually added.
        vertices[0] = new Vector2(-0.2f, -0.2f);    // (3) Add the vertices for a triangle.
        vertices[1] = new Vector2(0.2f, -0.2f);
        vertices[2] = new Vector2(-0.2f, 0.2f);
        vertices[3] = new Vector2(0.2f, 0.2f);
        box.set(vertices);                          // (4) And put them in the shape.
        fixture = body.createFixture(box, 1);       // (5) Create the fixture.
        fixture.setUserData("spike");               // (6) And set the user data to enemy.
        box.dispose();                              // (7) Destroy the shape when you don't need it.

        // Position the actor in the screen by converting the meters to pixels.
        setPosition((x - 0.5f) * PIXELS_IN_METERS, y * PIXELS_IN_METERS);
        setSize(PIXELS_IN_METERS, PIXELS_IN_METERS);
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
