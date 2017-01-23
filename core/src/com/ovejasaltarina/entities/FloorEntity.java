package com.ovejasaltarina.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ovejasaltarina.utils.Constants;

/**
 * Created by andrearodriguez on 1/13/17.
 */

public class FloorEntity extends Actor {
    private Texture floor, overfloor;
    private World world;
    private Body body, leftBody;
    private Fixture fixture, leftFixture;

    public FloorEntity(World world, Texture floor, Texture overfloor,  float x, float width, float y) {
        this.floor = floor;
        this.world = world;
        this.overfloor = overfloor;

        BodyDef def = new BodyDef();
        def.position.set(x + width / 2, y - 0.5f);  // (2) Center the floor in the coordinates given
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();      // (1) Create the polygon shape.
        box.setAsBox(width / 2, 0.5f);              // (2) Give it some size.
        fixture = body.createFixture(box, 3);       // (3) Create a fixture.
        fixture.setUserData("floor");
        box.dispose();

        BodyDef leftDef = new BodyDef();
        leftDef.position.set(x, y - 0.55f);
        leftBody = world.createBody(leftDef);

        PolygonShape leftBox = new PolygonShape();
        leftBox.setAsBox(0.02f, 0.3f);
        leftFixture = leftBody.createFixture(leftBox, 1);
        leftFixture.setUserData("spike");
        leftBox.dispose();

        setSize(width * Constants.PIXELS_IN_METERS, Constants.PIXELS_IN_METERS);
        setPosition(x * Constants.PIXELS_IN_METERS, (y - 1) * Constants.PIXELS_IN_METERS);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(floor, getX(), getY(), getWidth(), getHeight());
        batch.draw(overfloor, getX(), getY() + 0.9f * getHeight(), getWidth(), 0.1f * getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
