package com.maingame.game.sprites;

import java.util.HashMap;

public class Obstacles {
    protected String name, img;
    protected int impactDamage;

   //HashMap<String, String> ObstacleImg = new HashMap<String, String>();


    public Obstacles(String n){
        name = n;
    }

    public boolean checkHit(){
        return false;
    }


}
