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
    public final String colour;
    public final List<Texture> images;
    public final Rectangle collisionBounds;
    public final int speed;
    public final int maneuverability;
    public final int robustness;
    public final int acceleration;
    private int timePenalty;
    private int health=100;
    private int penaltyBar = 100;
    private int fatigue = 300;
    private int posX;
    private int posY;
    private final HashMap<String, List<Texture>> boatImg = new HashMap<>(); //Contains the images for the boats in an array of strings. The strings are the file names of the images.
    private final HashMap<String, Integer[]> boatMap = new HashMap<>(); //Contains the attributes for each boat in an array of integers. 0 for speed 1 for acceleration 2 robustness 3 maneuverability.
    private float leftBound;
    private float rightBound; // The left and right edges of a boat's lane.
    private final float maxFrameTime;
    private float currentFrameTime; // The maximum time allowed allowed for a frame. The current time for a frame.
    private int frame; // a integer representing which boat texture would be loaded.
    private boolean hasLost;

    public Boat(String col){
        this.buildBoatData();
        colour = col;
        speed = boatMap.get(col)[0];
        acceleration =  boatMap.get(col)[1];
        robustness = boatMap.get(col)[2];
        maneuverability = boatMap.get(col)[3];
        posX = boatMap.get(col)[4];
        posY = boatMap.get(col)[5];
        images = boatImg.get(col);
        leftBound = 0;
        rightBound = 0;
        timePenalty = 0;
        frame = 0;
        maxFrameTime = 0.5f/2;
        collisionBounds = new Rectangle(posX, posY,80,80);
        hasLost = false;
    }

    /**
     * Builds the hashmaps for the boat data.
     */
    private void buildBoatData() {
        Texture a = new Texture("Boat1.1.png");
        Texture b = new Texture("Boat1.2.png");
        List<Texture> arr = new ArrayList<>();
        arr.add(a);
        arr.add(b);
        boatImg.put("red", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat2.1.png");
        b = new Texture("Boat2.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("pink", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat3.1.png");
        b = new Texture("Boat3.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("blue", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat4.1.png");
        b = new Texture("Boat4.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("yellow", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat5.1.png");
        b = new Texture("Boat5.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("orange", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat6.1.png");
        b = new Texture("Boat6.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("green", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat7.1.png");
        b = new Texture("Boat7.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("purple", arr);

        Integer[] arr2 = {7,7,7,7,0,0};
        boatMap.put("red", arr2);
        arr2 = new Integer[]{8, 6, 8, 6,0,0};
        boatMap.put("pink", arr2);
        arr2 = new Integer[]{9, 9, 4, 5,0,0};
        boatMap.put("blue", arr2);
        arr2 = new Integer[]{8, 8, 5, 7,0,0};
        boatMap.put("yellow", arr2);
        arr2 = new Integer[]{8, 5, 10, 5,0,0};
        boatMap.put("orange", arr2);
        arr2 = new Integer[]{6, 6, 9, 7,0,0};
        boatMap.put("green", arr2);
        arr2 = new Integer[]{6, 6, 6, 10,0,0};
        boatMap.put("purple", arr2);
    }

    /**
     * Checks if a boat is outside of its lane.
     * If yes, it decreases the penaltyBar.
     */
    public void isBoatOutOfLane() {
        if( posX < leftBound || posX > rightBound) {
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
        List<Boat> newBoats = new ArrayList<>(boats);
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
        for (Boat enemyBoat : newBoats) {
            if (collisionBounds.overlaps(enemyBoat.collisionBounds)) {
                if (health - 2 < 0) {
                    health = 0;
                } else {
                    health -= 2;
                }
            }
        }
    }

    public int getTimePenalty() {
        return timePenalty;
    }

    public void setTimePenalty(int timePenalty) {
        this.timePenalty = timePenalty;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPenaltyBar() {
        return penaltyBar;
    }

    public void setPenaltyBar(int penaltyBar) {
        this.penaltyBar = penaltyBar;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public float getLeftBound() {
        return leftBound;
    }

    public float getRightBound() {
        return rightBound;
    }

    public int getFrame() {
        return frame;
    }

    public boolean isHasNotLost() {
        return !hasLost;
    }

    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }
}
