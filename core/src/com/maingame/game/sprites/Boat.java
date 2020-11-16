package com.maingame.game.sprites;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Boat {
    //Attributes
    public String colour;
    public List<Texture> images;
    private Rectangle bounds;
    public int speed, maneuverability, robustness, acceleration, health = 100,PosX,PosY,time;
    private HashMap<String, List<Texture>> BoatImg = new HashMap<String, List<Texture>>();
    private HashMap<String, Integer[]>  BoatMap = new HashMap<String, Integer[]>();
    public float leftBound, rightBound;

    public Boat(String col){
        this.buildBoatData();
        colour = col;
        speed = BoatMap.get(col)[0];
        acceleration =  BoatMap.get(col)[1];
        robustness = BoatMap.get(col)[2];
        maneuverability = BoatMap.get(col)[3];
        PosX = BoatMap.get(col)[4];
        PosY = BoatMap.get(col)[5];
        images = BoatImg.get(col);
        leftBound = 0;
        rightBound = 0;
    }
//  TODO: Change the values for the boats.
//  Builds the hashmaps for the boat data.
//  BoatImg contains the images for the boats in an array of strings. The strings are the file names of the images.
//  BoatMap contains the attributes for each boat in an array of integers.
//  0 for speed 1 for acceleration 2 robustness 3 maneuverability
    private void buildBoatData() {
        Texture a = new Texture("Boat1.1.png");
        Texture b = new Texture("Boat1.2.png");
        List<Texture> arr = new ArrayList<Texture>();
        arr.add(a);
        arr.add(b);
        BoatImg.put("red", arr);
        arr = new ArrayList<Texture>();
        a = new Texture("Boat2.1.png");
        b = new Texture("Boat2.2.png");
        arr.add(a);
        arr.add(b);
        BoatImg.put("pink", arr);
        arr = new ArrayList<Texture>();
        a = new Texture("Boat3.1.png");
        b = new Texture("Boat3.2.png");
        arr.add(a);
        arr.add(b);
        BoatImg.put("blue", arr);
        arr = new ArrayList<Texture>();
        a = new Texture("Boat4.1.png");
        b = new Texture("Boat4.2.png");
        arr.add(a);
        arr.add(b);
        BoatImg.put("yellow", arr);
        arr = new ArrayList<Texture>();
        a = new Texture("Boat5.1.png");
        b = new Texture("Boat5.2.png");
        arr.add(a);
        arr.add(b);
        BoatImg.put("orange", arr);
        arr = new ArrayList<Texture>();
        a = new Texture("Boat6.1.png");
        b = new Texture("Boat6.2.png");
        arr.add(a);
        arr.add(b);
        BoatImg.put("green", arr);
        arr = new ArrayList<Texture>();
        a = new Texture("Boat7.1.png");
        b = new Texture("Boat7.2.png");
        arr.add(a);
        arr.add(b);
        BoatImg.put("purple", arr);

        Integer[] arr2 = {10,10,10,10,0,0};
        BoatMap.put("red", arr2);
        arr2 = new Integer[]{9, 9, 9, 9,0,0};
        BoatMap.put("pink", arr2);
        arr2 = new Integer[]{8, 8, 8, 8,0,0};
        BoatMap.put("blue", arr2);
        arr2 = new Integer[]{7, 7, 7, 7,0,0};
        BoatMap.put("yellow", arr2);
        arr2 = new Integer[]{6, 6, 6, 6,0,0};
        BoatMap.put("orange", arr2);
        arr2 = new Integer[]{5, 5, 5, 5,0,0};
        BoatMap.put("green", arr2);
        arr2 = new Integer[]{4, 4, 4, 4,0,0};
        BoatMap.put("purple", arr2);
    }
    // Checks if a boat is outside of its lane and decreases its health
    public void isBoatOutOfLane() {
        if( PosX < leftBound || PosX > rightBound) {
            if (health - 0.00000005 < 0) {
                health = 0;
            }else {
                health -= 0.00000005;
            }
        }
    }
    // Changes lane boundaries for a boat
    public void setBounds(float leftBound, float rightBound){
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int h){
        health = h;
    }

}
