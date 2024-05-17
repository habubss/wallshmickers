package ru.itl.wallshmickers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {

    public Body body;
    public boolean isLeftContact;
    public boolean hasFirstJump;
    public boolean hasSecondJump;


    Texture axoright;
    Texture axoleft;
    Fixture fixture;

    private Vector2 size;

    public Player() {
        axoright = new Texture("axoright.png");
        axoleft = new Texture("axoleft.png");
    }

    public void createBody(World world, Vector2 pos, Vector2 size) {
        this.size = size;

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(pos);
        bodyDef.fixedRotation = true;

        // Create our body in the world using our body definition
        body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
//        CircleShape circle = new CircleShape();
//        circle.setRadius(6f);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 10000f;
//        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();

        fixture.setUserData("player");
    }

    public void draw(SpriteBatch batch) {
        boolean isRightView = isLeftContact;
        if (!hasSecondJump)
            isRightView = !isRightView;
        batch.draw(
                isRightView ? axoright : axoleft,
                body.getPosition().x - size.x,
                body.getPosition().y - size.y,
                size.x * 2, size.y * 2);
    }

    public void dispose() {
        axoright.dispose();
        axoleft.dispose();
    }
}
