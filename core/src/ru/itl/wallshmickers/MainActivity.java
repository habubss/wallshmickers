package ru.itl.wallshmickers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import jdk.tools.jmod.Main;
import states.Pause;

public class MainActivity extends ApplicationAdapter {

    public static int WIDTH = 720;
    public static int HEIGHT = 1280;
    private GameStateManager gsm;

    private SpriteBatch batch;

    private float accumulator = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new Pause(gsm));

/*
        cam = new OrthographicCamera(640, 640);
        cam.update();
        batch = new SpriteBatch();

        gsm = new GameStateManager();

        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();

        new Border().createBody(world, new Vector2(0, 0), new Vector2(Gdx.graphics.getWidth(), 10), true);

        int cordx = 400;
        int cordy = 80;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                cordx = 400;
                new Border().createBody(world, new Vector2(cordx, cordy), new Vector2(5, 20), false);
            } else {
                cordx /= 1.5;
                new Border().createBody(world, new Vector2(cordx, cordy), new Vector2(5, 20), true);

            }
            cordy += 80;
        }

        player = new Player();
        player.createBody(world, new Vector2(Gdx.graphics.getWidth() / 2f, 20), new Vector2(10, 10));

        createContactListener();
*/
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);

        /*if(player.body.getPosition().y+75>=cam.position.y) {
            cam.position.set(cam.viewportWidth / 2f, player.body.getPosition().y + 75, 0);
        }

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        if (Gdx.input.justTouched()) {
            if (player.hasFirstJump) {
                player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
                player.body.applyForceToCenter(
                        player.isLeftContact ? 250000 : -250000,
                        500000, true);
                player.hasFirstJump = false;
            } else if (player.hasSecondJump) {
                player.body.setLinearVelocity(0, 0);
                player.body.applyForceToCenter(
                        player.isLeftContact ? -250000 : 250000,
                        500000, true);
                player.hasSecondJump = false;
            }
        }

        doPhysicsStep(Gdx.graphics.getDeltaTime() * 10);

        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        player.draw(batch);
        batch.end();

        debugRenderer.render(world, batch.getProjectionMatrix());*/
    }

    /*private void doPhysicsStep(float deltaTime) {
        float timeStep = 1 / 60f;
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= timeStep) {
            world.step(timeStep, 6, 2);
            accumulator -= timeStep;
        }
    }*/

    /*private void createContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();

                if (a.getUserData().equals("player") && b.getUserData().equals("ground_left")
                        || b.getUserData().equals("player") && a.getUserData().equals("ground_left")) {
                    player.body.setGravityScale(0);
                    player.isLeftContact = true;
                    player.hasFirstJump = true;
                    player.hasSecondJump = true;
                } else if (a.getUserData().equals("player") && b.getUserData().equals("ground_right")
                        || b.getUserData().equals("player") && a.getUserData().equals("ground_right")) {
                    player.body.setGravityScale(0);
                    player.isLeftContact = false;
                    player.hasFirstJump = true;
                    player.hasSecondJump = true;
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();

                if (a.getUserData().equals("player") && b.getUserData().equals("ground_left")
                        || b.getUserData().equals("player") && a.getUserData().equals("ground_left")) {
                    player.body.setGravityScale(1);
                } else if (a.getUserData().equals("player") && b.getUserData().equals("ground_right")
                        || b.getUserData().equals("player") && a.getUserData().equals("ground_right")) {
                    player.body.setGravityScale(1);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
    */
}
