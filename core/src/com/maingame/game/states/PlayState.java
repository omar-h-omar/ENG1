package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;

import java.util.List;

public class PlayState extends State{
    private Texture river, riverReversed;
    private Texture boat;
    private List<Boat> boats;
    private int leg;
    private Boat player;
    private float riverPos1, riverPos2; // A tracker for the positions of the river assets
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),false); // a font to draw text
    private Pixmap pixmap, pixmap2; // a map to render the health bar.

    public PlayState(GameStateManager gsm, List<Boat> boats,Boat player,int leg){
        super(gsm);
        river = new Texture("river.png");
        riverReversed = new Texture("river_reversed.png");
        pixmap = new Pixmap(200,30, Pixmap.Format.RGBA8888);
        pixmap2 = new Pixmap(210,40, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.valueOf("eeeded"));
        pixmap.setColor(Color.valueOf("345830"));
        pixmap2.fill();
        pixmap.fill();
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
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.PosX -= player.maneuverability/2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.PosX += player.maneuverability/2;
        }
        // remove once collisions are implemented
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            if (player.health -1 < 0){
                player.health = 0;
            }else {
            player.health -= 1;
            }
        }
        cam.update();
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateRiver();
        boatsOutOfBounds();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        // Checks which leg it is and renders the boats and the background
        if (leg == 1) {
            sb.draw(river, 0, riverPos1);
            sb.draw(riverReversed, 0, riverPos2);

            sb.draw(riverReversed, river.getWidth(), riverPos1);
            sb.draw(river, river.getWidth(), riverPos2);
            sb.draw(river, river.getWidth() * 2, riverPos1);
            sb.draw(riverReversed, river.getWidth() * 2, riverPos2);
            sb.draw(riverReversed, river.getWidth() * 3, riverPos1);
            sb.draw(river, river.getWidth() * 3, riverPos2);
            sb.draw(river, river.getWidth() * 4, riverPos1);
            sb.draw(riverReversed, river.getWidth() * 4, riverPos2);

            Boat boat = boats.get(0);
            boat.setBounds(0,river.getWidth()-50);
            sb.draw(boat.images.get(0), boat.PosX,boat.PosY,100,100);
            boat = boats.get(1);
            boat.setBounds(river.getWidth()-50,river.getWidth()*2-50);
            sb.draw(boat.images.get(0), boat.PosX,boat.PosY,100,100);
            boat = player;
            boat.setBounds(river.getWidth()*2-50,river.getWidth()*3-50);
            sb.draw(boat.images.get(0),boat.PosX,boat.PosY,100,100);
            boat = boats.get(2);
            boat.setBounds(river.getWidth()*3-50,river.getWidth()*4-50);
            sb.draw(boat.images.get(0), boat.PosX,boat.PosY,100,100);
            boat = boats.get(3);
            boat.setBounds(river.getWidth()*4-50,river.getWidth()*5-50);
            sb.draw(boat.images.get(0), boat.PosX,boat.PosY,100,100);
        }

        // Controls the colour of the health bar
        if (player.health <= 25){
            pixmap.setColor(Color.valueOf("823038"));
        }else if (player.health <=50){
            pixmap.setColor(Color.valueOf("F95738"));
        }else if (player.health <= 75){
            pixmap.setColor(Color.valueOf("F2BB05"));
        }
        pixmap.fill();
        Texture pix2 = new Texture(pixmap2);
        Texture pix = new Texture(pixmap);
        font.draw(sb,"Health: ",cam.position.x/2 - pix.getWidth() - 200,cam.position.y + 358);
        sb.draw(pix2,cam.position.x/2 - pix2.getWidth() ,cam.position.y + 310);
        int healthBar = (player.health * 200)/100;
        sb.draw(pix,cam.position.x/2 - pix.getWidth() - 5,cam.position.y + 315,healthBar,30);
        sb.end();
    }

    @Override
    public void dispose() {

    }
    // Repositions the river assets once it goes out of the player's view
    private void updateRiver() {
        if (player.PosY > riverPos1 + river.getHeight()){
            riverPos1 += river.getHeight() * 2;
        }else if (player.PosY > riverPos2 + river.getHeight()){
            riverPos2 += river.getHeight()* 2;
        }
    }
    // Checks if boats are out of their lanes and decreases health
    private void boatsOutOfBounds() {
        for (int i = 0; i<boats.size()-1; i++) {
            boats.get(i).isBoatOutOfLane();
        }
        player.isBoatOutOfLane();
    }
}
