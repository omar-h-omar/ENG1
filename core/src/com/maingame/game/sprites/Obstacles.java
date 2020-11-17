package com.maingame.game.sprites;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Obstacles {
    protected String name;
    protected Texture img;
    protected int impactDamage, posX, posY;

    HashMap<String, Texture> ObstacleImg = new HashMap<String, Texture>();
    HashMap<String, Integer> ObstacleDamage = new HashMap<String, Integer>();


    public Obstacles(String n) {
        name = n;
        buildObstacleData();
        img = ObstacleImg.get(n);
        impactDamage = ObstacleDamage.get(n);

    }

    public boolean checkHit() {
        return false;
    }

    private void buildObstacleData() {

        Texture a = new Texture("rock1.png");
        ObstacleImg.put("rock1", a);

        a = new Texture("rock2.png");
        ObstacleImg.put("rock2", a);

        a = new Texture("goose.png");
        ObstacleImg.put("goose", a);

        a = new Texture("duck1.png");
        ObstacleImg.put("duck1", a);

        a = new Texture("duck2.png");
        ObstacleImg.put("duck2", a);

        ObstacleDamage.put("rock1", 10);
        ObstacleDamage.put("rock2", 10);
        ObstacleDamage.put("goose", 15);
        ObstacleDamage.put("duck1", 5);
        ObstacleDamage.put("duck2", 5);

    }

    public void setPosition(int a, int b){
        posX = a;
        posY = b;
    }

}


}