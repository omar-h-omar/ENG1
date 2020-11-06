package com.maingame.game.sprites;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class Boat {
    //Attributes
    private String colour, img;
    private Rectangle bounds;
    private int velocityX, velocityY, maneuverability, robustness, maxAcceleration, health = 100;
    private boolean isPlayer;

    List<String> available = new ArrayList<String>();
    available.add("red");
    available.add("pink");
    available.add("blue");
    available.add("yellow");
    available.add("orange");
    available.add("green");
    available.add("purple");

    HashMap<String, Integer[]>  BoatMap = new HashMap<String, Integer[]>();

    HashMap<String, String[]> BoatImg = new HashMap<String, String[]>();

    String[] arr = {"Boat1.1.png", "Boat1.2.png"};
    BoatImg.put("red", arr);
    arr = {"Boat2.1.png", "Boat2.2.png"};
    BoatImg.put("pink", arr);
    arr = {"Boat2.1.png", "Boat2.2.png"};
    BoatImg.put("pink", arr);
    arr = {"Boat3.1.png", "Boat3.2.png"};
    BoatImg.put("blue", arr);
    arr = {"Boat4.1.png", "Boat4.2.png"};
    BoatImg.put("yellow", arr);
    arr = {"Boat5.1.png", "Boat5.2.png"};
    BoatImg.put("orange", arr);
    arr = {"Boat6.1.png", "Boat6.2.png"};
    BoatImg.put("green", arr);
    arr = {"Boat7.1.png", "Boat7.2.png"};
    BoatImg.put("purple", arr);



    public Boat(String col){
        colour = col;
        maneuverability = BoatMap.get(col)[0];
        robustness = BoatMap.get(col)[1];
        maxAcceleration =  BoatMap.get(col)[2];
        img = BoatImg.get(col)[0];
        available.remove(col);
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int h){
        health = h;
    }

    public boolean collide(object obj){
        return false;
    }

}
