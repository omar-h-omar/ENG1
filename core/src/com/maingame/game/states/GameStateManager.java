package com.maingame.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Manages which state is shown to the user and transition between states using a stack.
 */
public class GameStateManager {
    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    /**
     * Puts the input state at the top of the stack.
     * @param state a class of all the game logic and assets at a given position in the game.
     * @see State
     */
    public void push(State state) {
        states.push(state);
    }

    /**
     * Removes the top state on the stack.
     */
    public void pop() {
        states.pop().dispose();
    }

    /**
     * Replaces the top state on the stack with the input state.
     * @param state a class of all the game logic and assets at a given position in the game.
     * @see State
     */
    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Updates the delta time of the top state on the stack
     * @param dt the time between each start of a render()
     */
    public void update(float dt) {
        states.peek().update(dt);
    }

    /**
     * Renders the top state on the stack
     * @param sb a batch for drawing objects
     * @see State#update(float)
     */
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
