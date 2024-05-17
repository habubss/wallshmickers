package ru.itl.wallshmickers;

import java.util.Stack;

public class GameStateManager extends states.GameStateManager {

    private Stack<MainActivity> states;

    public GameStateManager(){
        states = new Stack<MainActivity>();
    }
    public void push(MainActivity state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(MainActivity state){
        states.pop().dispose();
        states.push(state);
    }


}
