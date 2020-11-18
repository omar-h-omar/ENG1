package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;
import com.maingame.game.sprites.Obstacles;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PlayState extends State{
    private Texture river, riverReversed;
    private Texture boat;
    private List<Boat> boats;
    private int leg;
    private long time, countDown;
    private Boat player;
    private float riverPos1, riverPos2; // A tracker for the positions of the river assets
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),false); // a font to draw text
    private Pixmap healthMap, healthMap2; // a map to render the health bar.
    private Pixmap fatigueMap, fatigueMap2; // a map to render the fatigue bar.
    private Pixmap penaltyMap, penaltyMap2; // a map to render the penalty bar.
    private List<Obstacles> obstaclesList = new ArrayList<Obstacles>();

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public PlayState(GameStateManager gsm, List<Boat> boats,Boat player,int leg){
        super(gsm);
        river = new Texture("river.png");
        riverReversed = new Texture("river_reversed.png");

        healthMap = new Pixmap(200,30, Pixmap.Format.RGBA8888);
        healthMap2 = new Pixmap(210,40, Pixmap.Format.RGBA8888);
        fatigueMap = new Pixmap(200,30, Pixmap.Format.RGBA8888);
        fatigueMap2 = new Pixmap(210,40, Pixmap.Format.RGBA8888);
        penaltyMap = new Pixmap(200,30, Pixmap.Format.RGBA8888);
        penaltyMap2 = new Pixmap(210,40, Pixmap.Format.RGBA8888);
        healthMap.setColor(Color.valueOf("345830"));
        healthMap2.setColor(Color.valueOf("eeeded"));
        fatigueMap.setColor(Color.valueOf("345830"));
        fatigueMap2.setColor(Color.valueOf("eeeded"));
        penaltyMap.setColor(Color.valueOf("345830"));
        penaltyMap2.setColor(Color.valueOf("eeeded"));
        healthMap.fill();
        healthMap2.fill();
        fatigueMap.fill();
        fatigueMap2.fill();
        penaltyMap.fill();
        penaltyMap2.fill();

        this.leg = leg;
        this.boats = boats;
        this.player = player;
        if (leg != 4){
            cam.setToOrtho(false, river.getWidth()*5,MainGame.HEIGHT);
            boats.get(0).PosX = river.getWidth()/2-50;
            boats.get(1).PosX = river.getWidth()/2 + river.getWidth()-50;
            player.PosX = river.getWidth()/2 + (river.getWidth()*2)-50;
            boats.get(2).PosX = river.getWidth()/2 + (river.getWidth()*3)-50;
            boats.get(3).PosX = river.getWidth()/2 + (river.getWidth()*4)-50;
        }
        riverPos1 = 0;
        riverPos2 = river.getHeight();

        this.buildObstaclesList(leg);
        countDown = System.currentTimeMillis();
    }

    @Override
    public void handleInput() {
        player.PosY += player.speed;
        cam.position.y += player.speed;
        if (time == 0){
            time = System.currentTimeMillis();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.UP))){
            player.PosY += player.acceleration;
            cam.position.y += player.acceleration;
            if (player.fatigue -1 < 0){
                player.fatigue = 0;
            }else {
                player.fatigue -= 0.00001;
            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.PosX -= player.maneuverability/2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.PosX += player.maneuverability/2;
        }
        cam.update();
    }

    @Override
    public void update(float dt) {
        if ((System.currentTimeMillis() - countDown)/1000 > 3) {
            handleInput();
            updateCollisionBoundaries();
            updateRiver();
            collisionDetection();
            boatsOutOfBounds();
            updateMapColour();
            repositionObstacles();
            updateBoatPenalties();
            for (int i = 0; i < boats.size() - 1; i++) {
                boats.get(i).update(dt);
            }
            player.update(dt);
        }
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
            sb.draw(boat.images.get(boat.frame), boat.PosX,boat.PosY,100,100);
            boat = boats.get(1);
            boat.setBounds(river.getWidth()-50,river.getWidth()*2-50);
            sb.draw(boat.images.get(boat.frame), boat.PosX,boat.PosY,100,100);
            boat = player;
            boat.setBounds(river.getWidth()*2-50,river.getWidth()*3-50);
            sb.draw(boat.images.get(boat.frame),boat.PosX,boat.PosY,100,100);
            boat = boats.get(2);
            boat.setBounds(river.getWidth()*3-50,river.getWidth()*4-50);
            sb.draw(boat.images.get(boat.frame), boat.PosX,boat.PosY,100,100);
            boat = boats.get(3);
            boat.setBounds(river.getWidth()*4-50,river.getWidth()*5-50);
            sb.draw(boat.images.get(boat.frame), boat.PosX,boat.PosY,100,100);
        }
        Texture pix2 = new Texture(fatigueMap2);
        Texture pix = new Texture(fatigueMap);
        font.draw(sb,"Fatigue: ",cam.position.x/2 - pix.getWidth() - 200,cam.position.y + 358);
        sb.draw(pix2,cam.position.x/2 - pix2.getWidth() ,cam.position.y + 310);
        int fatigueBar = (player.fatigue * 200)/300;
        sb.draw(pix,cam.position.x/2 - pix.getWidth() - 5,cam.position.y + 315,fatigueBar,30);
        pix2 = new Texture(healthMap2);
        pix = new Texture(healthMap);
        font.draw(sb,"Health: ",cam.position.x/2 - pix.getWidth() - 200,cam.position.y + 308);
        sb.draw(pix2,cam.position.x/2 - pix2.getWidth() ,cam.position.y + 260);
        int healthBar = (player.health * 200)/100;
        sb.draw(pix,cam.position.x/2 - pix.getWidth() - 5,cam.position.y + 265,healthBar,30);
        pix2 = new Texture(penaltyMap2);
        pix = new Texture (penaltyMap);
        font.draw(sb, "Penalty: ",cam.position.x/2 - pix.getWidth() - 200,cam.position.y + 260);
        sb.draw(pix2, cam.position.x/2 - pix2.getWidth(), cam.position.y + 212);
        int penaltyBar = (player.penaltyBar * 200)/100;
        sb.draw(pix, cam.position.x/2 - pix.getWidth() - 5,cam.position.y + 217, penaltyBar, 30);

//        shapeRenderer.setProjectionMatrix(cam.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < obstaclesList.size() - 1; i++) {
            Obstacles obstacle = obstaclesList.get(i);
            sb.draw(obstacle.img,obstacle.posX,obstacle.posY,70,70);
//            shapeRenderer.rect(obstacle.collisionBounds.getX(),obstacle.collisionBounds.getY(),obstacle.collisionBounds.getWidth(),obstacle.collisionBounds.getHeight());
        }
//        shapeRenderer.rect(player.collisionBounds.getX(),player.collisionBounds.getY(),player.collisionBounds.getWidth(),player.collisionBounds.getHeight());
//        shapeRenderer.end();

        if (time != 0){
            font.draw(sb,"Time: " + ((System.currentTimeMillis() - time)/1000 + player.timePenalty) + "s" ,cam.position.x/2 - pix.getWidth() - 200,cam.position.y + 210);
        }
        if ((System.currentTimeMillis() - countDown)/1000 < 3) {
            font.draw(sb,"Countdown: " + (3 - (System.currentTimeMillis() - countDown)/1000),cam.position.x - 170,cam.position.y+50);
        }
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

    // Controls the colour of the bars
    private void updateMapColour() {
        if (player.health <= 25){
            healthMap.setColor(Color.valueOf("823038"));
        }else if (player.health <=50){
            healthMap.setColor(Color.valueOf("F95738"));
        }else if (player.health <= 75){
            healthMap.setColor(Color.valueOf("F2BB05"));
        }
        int fatiguePercent = player.fatigue*100/300;
        if (fatiguePercent <= 25){
            fatigueMap.setColor(Color.valueOf("823038"));
        }else if (fatiguePercent <=50){
            fatigueMap.setColor(Color.valueOf("F95738"));
        }else if (fatiguePercent <= 75){
            fatigueMap.setColor(Color.valueOf("F2BB05"));
        }
        if (player.penaltyBar <= 25){
            penaltyMap.setColor(Color.valueOf("823038"));
        }else if (player.penaltyBar <=50){
            penaltyMap.setColor(Color.valueOf("F95738"));
        }else if (player.penaltyBar <= 75){
            penaltyMap.setColor(Color.valueOf("F2BB05"));
        }else {
            penaltyMap.setColor(Color.valueOf("345830"));
        }
        healthMap.fill();
        fatigueMap.fill();
        penaltyMap.fill();
    }

//  Checks the current leg and generates a fixed amount of random Obstacles
    private void buildObstaclesList(int leg) {
        String[] possibleObstacles = {"rock1","rock2","goose","duck1","duck2"};
        int obstacleCount;
        if (leg == 1) {
            obstacleCount = 30;
        }else if (leg == 2) {
            obstacleCount = 30;
        }else {
            obstacleCount = 40;
        }
        for (int i = 0; i < obstacleCount; i++) {
            Random generator = new Random();
            Obstacles obstacle = new Obstacles(possibleObstacles[generator.nextInt(possibleObstacles.length)]);
            if (generator.nextFloat() > 0.5){
                obstacle.direction = true;
            }else {
                obstacle.direction = false;
            }
            this.obstaclesList.add(obstacle);
        }
        repositionObstacles();
    }

//  Repositions Obstacles
    private void repositionObstacles() {
        for (int i = 0; i < obstaclesList.size() - 1; i++){
            Obstacles obstacle = obstaclesList.get(i);
            if ((obstacle.posY + obstacle.img.getHeight() < player.PosY) || (obstacle.posX > river.getWidth() * 5) || (obstacle.posX + obstacle.img.getWidth() < 0) ){
                obstacle.posX = ThreadLocalRandom.current().nextInt(river.getWidth()*5);
                obstacle.posY = ThreadLocalRandom.current().nextInt(player.PosY + river.getHeight(),player.PosY + (river.getHeight()*3));
            }
            obstacle.moveObstacle();
        }
    }

//  Updates the collisionBoundaries for the boats and the obstacles
    private void updateCollisionBoundaries() {
        for (int i=0; i < boats.size() - 1; i++){
            Boat boat = boats.get(i);
            boat.collisionBounds.setPosition(boat.PosX+10,boat.PosY+10);
        }
        player.collisionBounds.setPosition(player.PosX+10,player.PosY+10);
        for (int i=0; i < obstaclesList.size() - 1; i++){
            Obstacles obstacle = obstaclesList.get(i);
            obstacle.updateCollisionBounds();
        }
    }
//  Detects collisions between obstacles and boats
    private void collisionDetection() {
        for (int x = 0; x < obstaclesList.size()-1; x++) {
            Obstacles obstacle = obstaclesList.get(x);
            for (int y = 0; y < boats.size() - 1; y++) {
                Boat boat = boats.get(y);
                obstacle.checkHit(boat);
            }
            obstacle.checkHit(player);
        }
    }

    private void updateBoatPenalties() {
        for (int i = 0; i < boats.size() - 1; i++) {
            Boat boat = boats.get(i);
            if (boat.penaltyBar == 0) {
                boat.penaltyBar = 100;
                boat.timePenalty += 2;
            }
        }
        if (player.penaltyBar == 0) {
            player.penaltyBar = 100;
            player.timePenalty += 2;
        }
    }
}
