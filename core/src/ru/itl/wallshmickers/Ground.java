package ru.itl.wallshmickers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground {
    public void createBody(World world, Vector2 pos, Vector2 size, boolean isLeft) {
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        // Set its world position
        groundBodyDef.position.set(pos);

        // Create a body from the definition and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(size.x, size.y);
        // Create a fixture from our polygon shape and add it to our ground body
        Fixture fixture = groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();
        fixture.setUserData("ground" + (isLeft ? "_left" : "_right"));
    }
}
