package states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import ru.itl.wallshmickers.GameScreen;
import states.GameStateManager;
import states.State;

public class DieState extends State {

    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();
    Preferences prefs = Gdx.app.getPreferences("game preferences");
    private Texture background;
    private float record;
    private float bestRecord;
    private float nowRecord;
    private float highscore = prefs.getFloat("highscore");

    GlyphLayout layout = new GlyphLayout(); //dont do this every frame! Store it as member

    BitmapFont font;
    OrthographicCamera cam;

    public DieState(GameStateManager gsm, float record, OrthographicCamera cam) {
        super(gsm);
        this.record = record;
        this.cam = cam;
        if (record > highscore) {
            highscore = record;
            prefs.putFloat("highscore", highscore);
            prefs.flush();
        }
        highscore = prefs.getFloat("highscore");
        background = new Texture("wshbackground.png");


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TNR.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 45;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

        layout.setText(font, "Best record: " + String.valueOf(highscore));
        bestRecord = layout.width;
        layout.setText(font, "Your record: " + String.valueOf(record));
        nowRecord = layout.width;

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new GameScreen((ru.itl.wallshmickers.GameStateManager) gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        cam.position.set(WIDTH / 2f, HEIGHT / 2f, 0);
        cam.update();
        sb.setProjectionMatrix(cam.combined);

        sb.begin();
        sb.draw(background, 0, 0, WIDTH, HEIGHT);
        font.draw(sb, "Record: " + String.valueOf((int) highscore),
                WIDTH / 4, HEIGHT / 2 + 150);
        font.draw(sb, "Score: " + String.valueOf((int) record),
                WIDTH / 4, HEIGHT / 2 + 50);
        font.draw(sb, "Tap to start a new game!", WIDTH / 4 - 130, HEIGHT / 2 - 50);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
