package com.maingame.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State{
    private Texture river;
    private Texture boat;

    public PlayState(GameStateManager gsm){
        super(gsm);
        river = new Texture("river.png");

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch ab) {

    }

    @Override
    public void dispose() {

    }
}
