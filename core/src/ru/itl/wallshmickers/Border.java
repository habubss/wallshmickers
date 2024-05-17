package ru.itl.wallshmickers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Border {
    public Body body;

    public Border createBody(World world, Vector2 pos, Vector2 size, SideType sideType) {
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        // Set its world position
        groundBodyDef.position.set(pos);
        // Create a body from the definition and add it to the world
        if (body == null)
            body = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(sideType == SideType.Center ? size.x - 1 : 1, size.y);
        // Create a fixture from our polygon shape and add it to our ground body
        Fixture fixture = body.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();

        String userDataText = "";
        switch (sideType) {
            case Left:
                userDataText = "ground_left";
                break;
            case Right:
                userDataText = "ground_right";
                break;
            case Center:
                userDataText = "ground_center";
                break;
        }
        fixture.setUserData(userDataText);

        return this;
    }

    public enum SideType {
        Left,
        Right,
        Center
    }
}
