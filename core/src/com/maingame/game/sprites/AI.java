package com.maingame.game.sprites;

import com.badlogic.gdx.math.Rectangle;
import java.util.List;


public class AI {
    private Boat boat;
    private List<Obstacle> obstacleList;
    private double randomVariable;
    public Rectangle farRightBox,midRightBox,midLeftBox,farLeftBox,rightSideBox,leftSideBox;

    public AI(Boat boat, int leg, List<Obstacle> obstacleList) {
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
    }
    public void update() {
        boat.PosY+=10;
        int weight = 0;
        for (int i = 0; i < obstacleList.size() - 1; i++) {
            Obstacle obstacle = obstacleList.get(i);
            if (overlapsFarRightBox(obstacle)){
                weight += 1;
            }
            if (overlapsMidRightBox(obstacle)) {
                weight += 2;
            }
            if (overlapsMidLeftBox(obstacle)) {
                weight -= 2;
            }
            if (overlapsFarLeftBox(obstacle)) {
                weight -= 1;
            }
            if (overlapsRightSideBox(obstacle)) {
                weight += 1;
            }
            if (overlapsLeftSideBox(obstacle)) {
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

    private boolean overlapsFarRightBox(Obstacle obstacle) {
        return farRightBox.overlaps(obstacle.collisionBounds);
    }

    private boolean overlapsFarLeftBox(Obstacle obstacle) {
        return farLeftBox.overlaps(obstacle.collisionBounds);
    }

    private boolean overlapsMidRightBox(Obstacle obstacle) {
        return midRightBox.overlaps(obstacle.collisionBounds);
    }

    private boolean overlapsMidLeftBox(Obstacle obstacle) {
        return midLeftBox.overlaps(obstacle.collisionBounds);
    }

    private boolean overlapsRightSideBox(Obstacle obstacle) {
        return rightSideBox.overlaps(obstacle.collisionBounds);
    }

    private boolean overlapsLeftSideBox(Obstacle obstacle) {
        return leftSideBox.overlaps(obstacle.collisionBounds);
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
