package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;

import java.util.List;

public class PlayState extends State{
    private Texture river;
    private Texture boat;
    private List<Boat> boats;
    private int leg;
    private Boat player;
    private float riverPos1, riverPos2;

    public PlayState(GameStateManager gsm, List<Boat> boats,Boat player,int leg){
        super(gsm);
        river = new Texture("river.png");
        this.leg = leg;
        this.boats = boats;
        this.player = player;
        if (leg == 1){
            cam.setToOrtho(false, river.getWidth()*5,MainGame.HEIGHT);
            boats.get(0).PosX = river.getWidth()/2-50;
            boats.get(1).PosX = river.getWidth()/2 + river.getWidth()-50;
            player.PosX = river.getWidth()/2 + (river.getWidth()*2)-50;
            boats.get(2).PosX = river.getWidth()/2 + (river.getWidth()*3)-50;
            boats.get(3).PosX = river.getWidth()/2 + (river.getWidth()*4)-50;
        }
        riverPos1 = 0;
        riverPos2 = river.getHeight();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.UP))){
            player.PosY += player.speed;
            cam.position.y += player.speed;
        }
        cam.update();
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateRiver();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        if (leg == 1) {
            sb.draw(river, 0, riverPos1);
            sb.draw(river, 0, riverPos2);
            Boat boat = boats.get(0);
            sb.draw(boat.images.get(0), boat.PosX,boat.PosY,100,100);
            sb.draw(river, river.getWidth(), riverPos1);
            sb.draw(river, river.getWidth(), riverPos2);
            boat = boats.get(1);
            sb.draw(boat.images.get(0), boat.PosX,boat.PosY,100,100);
            sb.draw(river, river.getWidth() * 2, riverPos1);
            sb.draw(river, river.getWidth() * 2, riverPos2);
            boat = player;
            sb.draw(boat.images.get(0), boat.PosX,boat.PosY,100,100);
            sb.draw(river, river.getWidth() * 3, riverPos1);
            sb.draw(river, river.getWidth() * 3, riverPos2);
            boat = boats.get(2);
            sb.draw(boat.images.get(0), boat.PosX,boat.PosY,100,100);
            sb.draw(river, river.getWidth() * 4, riverPos1);
            sb.draw(river, river.getWidth() * 4, riverPos2);
            boat = boats.get(3);
            sb.draw(boat.images.get(0), boat.PosX,boat.PosY,100,100);
        }
        sb.end();
    }

    @Override
    public void dispose() {

    }
    private void updateRiver() {
        if (player.PosY > riverPos1 + river.getHeight()){
            riverPos1 += river.getHeight() * 2;
        }else if (player.PosY > riverPos2 + river.getHeight()){
            riverPos2 += river.getHeight()* 2;
        }
    }
}
