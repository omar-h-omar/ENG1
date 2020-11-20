package com.maingame.game.sprites;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class to hold all the logic and attribute for boats.
 */
public class Boat {
    public String colour;
    public List<Texture> images;
    public Rectangle collisionBounds;
    public int speed, maneuverability, robustness, acceleration, timePenalty,health=100, penaltyBar = 100, fatigue = 300,PosX,PosY;
    private HashMap<String, List<Texture>> BoatImg = new HashMap<String, List<Texture>>(); //Contains the images for the boats in an array of strings. The strings are the file names of the images.
    private HashMap<String, Integer[]>  BoatMap = new HashMap<String, Integer[]>(); //Contains the attributes for each boat in an array of integers. 0 for speed 1 for acceleration 2 robustness 3 maneuverability.
    public float leftBound, rightBound; // The left and right edges of a boat's lane.
    private float maxFrameTime, currentFrameTime; // The maximum time allowed allowed for a frame. The current time for a frame.
    public int frame; // a integer representing which boat texture would be loaded.
    public boolean hasLost;

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
        timePenalty = 0;
        frame = 0;
        maxFrameTime = 0.5f/2;
        collisionBounds = new Rectangle(PosX,PosY,80,80);
        hasLost = false;
    }

//  TODO: Change the values for the boats.
    /**
     * Builds the hashmaps for the boat data.
     */
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

        Integer[] arr2 = {7,7,7,7,0,0};
        BoatMap.put("red", arr2);
        arr2 = new Integer[]{8, 6, 8, 6,0,0};
        BoatMap.put("pink", arr2);
        arr2 = new Integer[]{9, 9, 4, 5,0,0};
        BoatMap.put("blue", arr2);
        arr2 = new Integer[]{8, 8, 5, 7,0,0};
        BoatMap.put("yellow", arr2);
        arr2 = new Integer[]{8, 5, 10, 5,0,0};
        BoatMap.put("orange", arr2);
        arr2 = new Integer[]{6, 6, 9, 7,0,0};
        BoatMap.put("green", arr2);
        arr2 = new Integer[]{6, 6, 6, 10,0,0};
        BoatMap.put("purple", arr2);
    }

    /**
     * Checks if a boat is outside of its lane.
     * If yes, it decreases the penaltyBar.
     */
    public void isBoatOutOfLane() {
        if( PosX < leftBound || PosX > rightBound) {
            if (penaltyBar - 0.00000005 < 0) {
                penaltyBar = 0;
            }else {
                penaltyBar -= 0.00000005;
            }
        }else {
            if (penaltyBar > 0) {
                penaltyBar = 100;
            }
        }
    }

    /**
     * Sets the lane limits for a boat.
     * @param leftBound a float of where the boat lane starts
     * @param rightBound a float of where the boat lane ends
     */
    public void setBounds(float leftBound, float rightBound){
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    /**
     * Changes the current texture used for a boat to provide an animation.
     * @param dt the time between each start of a render()
     */
    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame ++;
            currentFrameTime = 0;
        }
        if (frame >= 2) {
            frame = 0;
        }
    }

    public void hasCollided(List<Boat> boats, Boat player) {
        List<Boat> newBoats = new ArrayList<Boat>(boats);
        if (collisionBounds != player.collisionBounds) {
            newBoats.remove(this);
            if (collisionBounds.overlaps(player.collisionBounds)) {
                if (health - 2 < 0){
                    health = 0;
                }else{
                    health -= 2;
                }
            }
        }
        for (int i = 0; i < newBoats.size(); i++) {
            Boat enemyBoat = newBoats.get(i);
            if (collisionBounds.overlaps(enemyBoat.collisionBounds)) {
                if (health - 2 < 0){
                    health = 0;
                }else{
                    health -= 2;
                }
            }
        }
    }

}
