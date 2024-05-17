package ru.itl.wallshmickers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sun.org.apache.xpath.internal.operations.Or;

import sun.jvm.hotspot.gc.shared.GCWhen;

public class BorderStar {
    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();
    Texture boardstar;
    private Vector2 pos;
    public boolean isBorder = true;
    public Texture getBoardstar(){
        return boardstar;
    }
    public Vector2 getPosBord(){
        return pos;
    }
    public BorderStar(float x, float y){
        boardstar = new Texture("bordstar.png");
        pos = new Vector2(x,y);
    }
    public void reposition(float x,float y){
        pos = new Vector2(x,y);
    }
}
