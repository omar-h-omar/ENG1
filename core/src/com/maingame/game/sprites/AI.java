package com.maingame.game.sprites;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class AI {
    private Boat boat;
    private List<Obstacle> obstacleList;
    private List<Boat> boats;
    private double randomVariable;
    public Rectangle farRightBox,midRightBox,midLeftBox,farLeftBox,rightSideBox,leftSideBox;

    public AI(Boat boat, int leg, List<Obstacle> obstacleList, List<Boat> boats, Boat player) {
        this.boat = boat;
        this.obstacleList = obstacleList;
        if (leg == 0) {
            randomVariable = 0.3;
        }
        farRightBox = new Rectangle(boat.PosX + 70,boat.PosY+90,20,50);
        midRightBox = new Rectangle(boat.PosX + 50,boat.PosY+90,20,50);
        midLeftBox = new Rectangle(boat.PosX + 30,boat.PosY+90,20,50);
        farLeftBox = new Rectangle(boat.PosX+10,boat.PosY+90,20,50);
        rightSideBox = new Rectangle(boat.PosX + 90,boat.PosY+10,30,130);
        leftSideBox = new Rectangle(boat.PosX - 20, boat.PosY+10, 30,130);
        this.boats = new ArrayList<Boat>(boats);
        this.boats.add(player);
        this.boats.remove(boat);
    }
    public void update() {
        boat.PosY+=10;
        int weight = 0;
        for (int i = 0; i < obstacleList.size(); i++) {
            Obstacle obstacle = obstacleList.get(i);
            if (farRightBox.overlaps(obstacle.collisionBounds)){
                weight += 1;
            }
            if (midRightBox.overlaps(obstacle.collisionBounds)) {
                weight += 2;
            }
            if (midLeftBox.overlaps(obstacle.collisionBounds)) {
                weight -= 2;
            }
            if (farLeftBox.overlaps(obstacle.collisionBounds)) {
                weight -= 1;
            }
            if (rightSideBox.overlaps(obstacle.collisionBounds)) {
                weight += 1;
            }
            if (leftSideBox.overlaps(obstacle.collisionBounds)) {
                weight -= 1;
            }
        }
        for (int i = 0; i < boats.size(); i++) {
            Boat enemyBoat = boats.get(i);
            if (farRightBox.overlaps(enemyBoat.collisionBounds)){
                weight += 1;
            }
            if (midRightBox.overlaps(enemyBoat.collisionBounds)) {
                weight += 2;
            }
            if (midLeftBox.overlaps(enemyBoat.collisionBounds)) {
                weight -= 2;
            }
            if (farLeftBox.overlaps(enemyBoat.collisionBounds)) {
                weight -= 1;
            }
            if (rightSideBox.overlaps(enemyBoat.collisionBounds)) {
                weight += 1;
            }
            if (leftSideBox.overlaps(enemyBoat.collisionBounds)) {
                weight -= 1;
            }
        }
        double chance = Math.random();
        if (weight > 0 && chance > randomVariable) {
            moveLeft(weight);
        }else if (weight < 0 && chance > randomVariable) {
            moveRight(weight);
        }
    }

    private void moveRight(int weight) {
        while (weight*2 < 0){
            if (!(boat.PosX + boat.speed > boat.rightBound)) {
                boat.PosX += boat.speed;
            }
            weight ++;
        }
    }

    private void moveLeft(int weight) {
        while (weight*2 > 0){
            if (!(boat.PosX - boat.speed < boat.leftBound)){
                boat.PosX -= boat.speed;
            }
            weight --;
        }
    }
}
