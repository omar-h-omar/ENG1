package com.maingame.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import com.badlogic.gdx.math.Rectangle;

/**
 * A class to hold all the logic and attribute for obstacles
 */
public class Obstacle {
    public String name;
    public Texture img;
    public int impactDamage, posX, posY;
    private boolean isMovable;
    public boolean direction;
    public Rectangle collisionBounds;

    HashMap<String, Integer> ObstacleDamage = new HashMap<String, Integer>();


    public Obstacle(String obstacleName) {
        name = obstacleName;
        buildObstacleData();
        img = new Texture(obstacleName + ".png");
        impactDamage = ObstacleDamage.get(obstacleName);
        posX = -9000;
        posY = -9000;

        if ((obstacleName == "rock1") || (obstacleName == "rock2")){
            isMovable = false;
            collisionBounds = new Rectangle(posX,posY,30,30);
        }else {
            isMovable = true;
            collisionBounds = new Rectangle(posX,posY,70,70);
        }
    }

    /**
     * Checks if a boat has collided with an obstacle.
     * If yes, the boat's health is decreased and repositions the obstacle.
     * @param boat a class representing a boat.
     */
    public void checkHit(Boat boat) {
        if (collisionBounds.overlaps(boat.collisionBounds)){
            boat.health -= ObstacleDamage.get(name);
            posX = -9000;
            posY = -9000;
            collisionBounds.setPosition(posX,posY);
            if (boat.health < 0) {
                boat.health = 0;
            }
        }
    }

    /**
     * Adds how much health would be lost from each obstacle impact.
     */
    private void buildObstacleData() {
        ObstacleDamage.put("rock1", 10);
        ObstacleDamage.put("rock2", 10);
        ObstacleDamage.put("goose", 15);
        ObstacleDamage.put("duck1", 5);
        ObstacleDamage.put("duck2", 5);

    }

    /**
     * Updates the collision bounds to match the current obstacle position
     */
    public void updateCollisionBounds() {
        if (name == "rock1") {
            collisionBounds = new Rectangle(posX+20,posY+20,30,30);
        }else if (name == "rock2"){
            collisionBounds = new Rectangle(posX+5,posY+35,30,25);
        }else if (name == "goose") {
            collisionBounds = new Rectangle(posX,posY+10,70,60);
        }else if (name == "duck1"){
            collisionBounds = new Rectangle(posX+20,posY+27,33,33);
        }else {
            collisionBounds = new Rectangle(posX+10,posY+10,40,50);
        }
    }

    /**
     * Moves movable obstacles on the screen.
     */
    public void moveObstacle() {
        if (isMovable) {
            if (!direction){
                posX += 1;
            }else {
                posX -= 1;
            }
        }
    }
}