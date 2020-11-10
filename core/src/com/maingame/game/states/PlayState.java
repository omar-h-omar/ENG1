package com.maingame.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;

import java.util.List;

public class PlayState extends State{
    private Texture river;
    private Texture boat;
    private List<Boat> boats;

    public PlayState(GameStateManager gsm, List<Boat> boats,Boat player){
        super(gsm);
        river = new Texture("river.png");
        this.boats = boats;
        cam.setToOrtho(false, river.getWidth(),MainGame.HEIGHT/2);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(river,0,0);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
