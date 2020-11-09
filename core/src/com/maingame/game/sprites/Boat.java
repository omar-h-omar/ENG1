package com.maingame.game.sprites;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;

public class Boat {
    //Attributes
    public String colour, img;
    private Rectangle bounds;
    public int speed, maneuverability, robustness, acceleration, health = 100;
    private boolean isPlayer;
    private HashMap<String, String[]> BoatImg = new HashMap<String, String[]>();
    private HashMap<String, Integer[]>  BoatMap = new HashMap<String, Integer[]>();


    public Boat(String col){
        this.buildBoatData();
        colour = col;
        speed = BoatMap.get(col)[0];
        acceleration =  BoatMap.get(col)[1];
        robustness = BoatMap.get(col)[2];
        maneuverability = BoatMap.get(col)[3];
        img = BoatImg.get(col)[0];
    }
//  TODO: Change the values for the boats.
//  Builds the hashmaps for the boat data.
//  BoatImg contains the images for the boats in an array of strings. The strings are the file names of the images.
//  BoatMap contains the attributes for each boat in an array of integers.
//  0 for speed 1 for acceleration 2 robustness 3 maneuverability
    private void buildBoatData() {
        String[] arr = {"Boat1.1.png", "Boat1.2.png"};
        BoatImg.put("red", arr);
        arr = new String[]{"Boat2.1.png", "Boat2.2.png"};
        BoatImg.put("pink", arr);
        arr = new String[]{"Boat3.1.png", "Boat3.2.png"};
        BoatImg.put("blue", arr);
        arr = new String[]{"Boat4.1.png", "Boat4.2.png"};
        BoatImg.put("yellow", arr);
        arr = new String[]{"Boat5.1.png", "Boat5.2.png"};
        BoatImg.put("orange", arr);
        arr = new String[]{"Boat6.1.png", "Boat6.2.png"};
        BoatImg.put("green", arr);
        arr = new String[]{"Boat7.1.png", "Boat7.2.png"};
        BoatImg.put("purple", arr);

        Integer[] arr2 = {10,10,10,10};
        BoatMap.put("red", arr2);
        arr2 = new Integer[]{9, 9, 9, 9};
        BoatMap.put("pink", arr2);
        arr2 = new Integer[]{8, 8, 8, 8};
        BoatMap.put("blue", arr2);
        arr2 = new Integer[]{7, 7, 7, 7};
        BoatMap.put("yellow", arr2);
        arr2 = new Integer[]{6, 6, 6, 6};
        BoatMap.put("orange", arr2);
        arr2 = new Integer[]{5, 5, 5, 5};
        BoatMap.put("green", arr2);
        arr2 = new Integer[]{4, 4, 4, 4};
        BoatMap.put("purple", arr2);
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int h){
        health = h;
    }

}
