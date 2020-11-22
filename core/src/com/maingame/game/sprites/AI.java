package com.maingame.game.sprites;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class AI {
    private final Boat boat;
    private final List<Obstacle> obstacleList;
    private final List<Boat> boats;
    private double randomVariable;
    public final Rectangle farRightBox;
    public final Rectangle midRightBox;
    public final Rectangle midLeftBox;
    public final Rectangle farLeftBox;
    public final Rectangle rightSideBox;
    public final Rectangle leftSideBox;

    public AI(Boat boat, int leg, List<Obstacle> obstacleList, List<Boat> boats, Boat player) {
        this.boat = boat;
        this.obstacleList = obstacleList;
        if (leg == 0) {
            randomVariable = 0.3;
        }
        farRightBox = new Rectangle((float) boat.getPosX() + 70,(float) boat.getPosY() +90,20,50);
        midRightBox = new Rectangle((float) boat.getPosX() + 50,(float) boat.getPosY() +90,20,50);
        midLeftBox = new Rectangle((float) boat.getPosX() + 30,(float) boat.getPosY() +90,20,50);
        farLeftBox = new Rectangle((float) boat.getPosX() +10,(float) boat.getPosY() +90,20,50);
        rightSideBox = new Rectangle((float) boat.getPosX() + 90,(float) boat.getPosY() +10,30,130);
        leftSideBox = new Rectangle((float) boat.getPosX() - 20, (float) boat.getPosY() +10, 30,130);
        this.boats = new ArrayList<>(boats);
        this.boats.add(player);
        this.boats.remove(boat);
    }
    public void update() {
        boat.setPosY(boat.getPosY() + boat.speed);
        int weight = 0;
        weight = isNearObstacles(weight);
        weight = isNearBoats(weight);

        double chance = Math.random();
        if (weight > 0 && chance > randomVariable) {
            moveLeft(weight);
        }else if (weight < 0 && chance > randomVariable) {
            moveRight(weight);
        }
    }

    private void moveRight(int weight) {
        while (weight < 0){
            if ((boat.getPosX() + boat.speed < boat.getRightBound())) {
                boat.setPosX(boat.getPosX() + boat.maneuverability/4);
            }
            weight ++;
        }
    }

    private void moveLeft(int weight) {
        while (weight > 0){
            if ((boat.getPosX() - boat.speed > boat.getLeftBound())){
                boat.setPosX(boat.getPosX() - boat.maneuverability/4);
            }
            weight --;
        }
    }

    private int isNearObstacles(int weight) {
        for (Obstacle obstacle : obstacleList) {
            if (farRightBox.overlaps(obstacle.getCollisionBounds())) {
                weight += 1;
            }
            if (midRightBox.overlaps(obstacle.getCollisionBounds())) {
                weight += 2;
            }
            if (midLeftBox.overlaps(obstacle.getCollisionBounds())) {
                weight -= 2;
            }
            if (farLeftBox.overlaps(obstacle.getCollisionBounds())) {
                weight -= 1;
            }
            if (rightSideBox.overlaps(obstacle.getCollisionBounds())) {
                weight += 1;
            }
            if (leftSideBox.overlaps(obstacle.getCollisionBounds())) {
                weight -= 1;
            }
        }
        return weight;
    }

    private int isNearBoats(int weight) {
        for (Boat enemyBoat : boats) {
            if (farRightBox.overlaps(enemyBoat.collisionBounds)) {
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
        return weight;
    }
}
