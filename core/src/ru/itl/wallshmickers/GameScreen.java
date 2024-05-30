package ru.itl.wallshmickers;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.Color;


import states.DieState;
import states.State;

public class GameScreen extends State {
    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();
    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    Texture bg;
    public static final int BG_SIZE = 1200;
    Texture ground;
    private float accumulator = 0;
    Player player;
    public static final int BORDERS_COUNT = 14;
    private Array<BorderStar> borders;
    private Array<Border> trueborders;
    public int bgposition = 2410;
    BitmapFont font;
    public float score = 0;
    public float siu = 160;
    Texture rightstart;
    Texture leftstart;



    public GameScreen(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera(WIDTH, HEIGHT);
        cam.update();




        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();

        borders = new Array<BorderStar>();
        trueborders = new Array<>();
        int borderposx = WIDTH / 2;
        int borderposy = 400;


        //rightstart border
        new Border().createBody(world,
                new Vector2(borderposx + WIDTH / 8 - 7, 110),
                new Vector2(7, 100),
                Border.SideType.Right);

        new Border().createBody(world,
                new Vector2(borderposx + WIDTH / 8 + 7, 110),
                new Vector2(7, 100),
                Border.SideType.Left);

        new Border().createBody(world,
                new Vector2(borderposx + WIDTH / 8, 110),
                new Vector2(7, 100),
                Border.SideType.Center);
        //rightstart border

        //leftstert border
        new Border().createBody(world,
                new Vector2(borderposx - WIDTH / 8 + 7, 160),
                new Vector2(7, 150),
                Border.SideType.Left);
        new Border().createBody(world,
                new Vector2(borderposx - WIDTH / 8 - 7, 160),
                new Vector2(7, 150),
                Border.SideType.Right);
        new Border().createBody(world,
                new Vector2(borderposx - WIDTH / 8, 160),
                new Vector2(7, 150),
                Border.SideType.Center);
        //leftstart border

        for (int i = 0; i < BORDERS_COUNT; i++) {
            if (i % 2 == 0) {
                borders.add(new BorderStar(borderposx + WIDTH / 8 - 7, borderposy - 35));

                trueborders.add(new Border().createBody(world,
                        new Vector2(borderposx + WIDTH / 8 - 7, borderposy),
                        new Vector2(7, 28),
                        Border.SideType.Right));

                trueborders.add(new Border().createBody(world,
                        new Vector2(borderposx + WIDTH / 8 + 7, borderposy),
                        new Vector2(7, 28),
                        Border.SideType.Left));


                trueborders.add(new Border().createBody(world,
                        new Vector2(borderposx + WIDTH / 8, borderposy),
                        new Vector2(7, 35),
                        Border.SideType.Center));

            }
            else if (i % 2 != 0 && Math.random()>0.5) {
                borders.add(new BorderStar(borderposx - WIDTH / 8 - 7, borderposy - 35));

                trueborders.add(new Border().createBody(world,
                        new Vector2(borderposx - WIDTH / 8 + 7, borderposy),
                        new Vector2(7, 28),
                        Border.SideType.Left));
                
                trueborders.add(new Border().createBody(world,
                        new Vector2(borderposx - WIDTH / 8 - 7, borderposy),
                        new Vector2(7, 28),
                        Border.SideType.Right));

                trueborders.add(new Border().createBody(world,
                        new Vector2(borderposx - WIDTH / 8, borderposy),
                        new Vector2(7, 35),
                        Border.SideType.Center));
            }
            borderposy += 125;
        }
        new Ground().createBody(world, new Vector2(0, 0), new Vector2(WIDTH, 10), true);
        bg = new Texture("wshbackground.png");
        ground = new Texture("wshground.png");
        rightstart = new Texture("starterborder.png");
        leftstart = new Texture("leftstartborder.png");
        player = new Player();
        player.createBody(world, new Vector2(WIDTH / 2, 20), new Vector2(25, 25));


        createContactListener();
        //font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TNR.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 60;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        if (Gdx.input.justTouched()) {
            if (player.hasFirstJump) {
                player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
                player.body.applyForceToCenter(
                        player.isLeftContact ? 1725000 : -1725000,
                        3750000, true);
                player.hasFirstJump = false;
            } else if (player.hasSecondJump) {
                player.body.setLinearVelocity(0, 0);
                player.body.applyForceToCenter(
                        player.isLeftContact ? -1725000 : 1725000,
                        3750000, true);
                player.hasSecondJump = false;
            }
        }
        for (BorderStar borderStar : borders) {
            if (cam.position.y - (cam.viewportHeight / 2) > borderStar.getPosBord().y + 35) {
                borderStar.reposition(borderStar.getPosBord().x, cam.position.y + (cam.viewportHeight / 2) + 155);
            }
        }

        for (int i = 0; i < trueborders.size; i++) {
            Border border1 = trueborders.get(i);
            if (cam.position.y - (cam.viewportHeight / 2) > border1.body.getPosition().y + 35) {
                border1.body.setTransform(
                        border1.body.getPosition().x,
                        cam.position.y + (cam.viewportHeight / 2) + 155,
                        border1.body.getAngle()
                );
            }
        }
        if(bgposition<(cam.viewportHeight / 2f) + cam.position.y){
            bgposition+=BG_SIZE;
        }
        if(player.body.getPosition().y>=siu){
            score += 1;
            siu+=240;
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        if (player.body.getPosition().y + 75 >= cam.position.y) {
            cam.position.set(cam.viewportWidth / 2f, player.body.getPosition().y + 75, 0);
        }
        cam.update();
        sb.setProjectionMatrix(cam.combined);
        if (player.body.getPosition().y <= cam.position.y - (cam.viewportHeight / 2f)) {
            gsm.set(new DieState(gsm, score, cam));
            score = 0;
        }

        if (player.body.getPosition().x <= cam.position.x - (cam.viewportWidth / 2f) ||
            player.body.getPosition().x >= cam.position.x + (cam.viewportWidth / 2f)) {
            gsm.set(new DieState(gsm, score, cam));
            score = 0;
        }


        doPhysicsStep(Gdx.graphics.getDeltaTime() * 10);

        ScreenUtils.clear(1, 0, 0, 1);
        sb.begin();

        int bgpos = 10;
        for (int i=0; i<100; i++){
            sb.draw(bg, 0, bgpos, WIDTH + 100, BG_SIZE);
            bgpos+=BG_SIZE;
        }
        sb.draw(bg, 0, bgposition, WIDTH + 100, BG_SIZE);
        sb.draw(ground, 0, -HEIGHT + 10, WIDTH, HEIGHT);
        player.draw(sb);
        for (int i = 0; i < borders.size; i++) {
            sb.draw(borders.get(i).getBoardstar(), borders.get(i).getPosBord().x, borders.get(i).getPosBord().y);
        }
        sb.draw(rightstart,WIDTH/2 + WIDTH/8 - 8, 10);
        sb.draw(leftstart,WIDTH/2 - WIDTH/8 - 8, 10);
        font.draw(sb, String.valueOf((int) score), 40, (cam.viewportHeight / 2f) + cam.position.y - 60);
        sb.end();

        //debugRenderer.render(world, sb.getProjectionMatrix());
    }

    @Override
    public void dispose() {
        player.dispose();
        bg.dispose();
        for (BorderStar border : borders) {
            border.getBoardstar().dispose();
        }
    }

    private void doPhysicsStep(float deltaTime) {
        float timeStep = 1 / 60f;
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= timeStep) {
            world.step(timeStep, 6, 2);
            accumulator -= timeStep;
        }
    }

    private void createContactListener() {
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
                } else if (a.getUserData().equals("player") && b.getUserData().equals("ground_center")
                        || b.getUserData().equals("player") && a.getUserData().equals("ground_center")) {
                    gsm.set(new DieState(gsm, score, cam));
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
}
