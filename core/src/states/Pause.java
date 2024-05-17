package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import jdk.tools.jmod.Main;
import ru.itl.wallshmickers.GameScreen;
import ru.itl.wallshmickers.MainActivity;

public class Pause extends State {

    private Texture background;
    private Texture playBtn;


    public Pause(GameStateManager gsm) {
        super(gsm);
        background = new Texture("wshbackground.png");
        playBtn = new Texture("wshplaybtn.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new GameScreen((ru.itl.wallshmickers.GameStateManager) gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, camera.position.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(playBtn, (Gdx.graphics.getWidth()/2) - (playBtn.getWidth()/2), (Gdx.graphics.getHeight()/2)-(playBtn.getHeight()/2));
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
