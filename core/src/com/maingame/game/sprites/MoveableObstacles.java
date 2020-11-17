package com.maingame.game.sprites;


public class MoveableObstacles extends Obstacles{
        protected int velocity;

    public MoveableObstacles(String n) {
        super(n);
    }

    public void move(){

        setPosition(posX + velocity, posY);

    }
}

