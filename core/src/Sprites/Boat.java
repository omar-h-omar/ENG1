package Sprites;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import java.util.List;
import java.util.HashMap;

public class Boat{
    //Attributes
    private String colour, img;
    private Rectangle bounds;
    private int velocityX, velocityY, maneuverability, robustness, maxAcceleration, health = 100;
    private boolean isPlayer;

    List<String> Available;
    HashMap<String, List<Integer>>  BoatMap;

    HashMap<String, String> BoatImg;


    public Boat(String col){
        colour = col;
        maneuverability = BoatMap.get(col).get(0);
        robustness = BoatMap.get(col).get(1);
        maxAcceleration =  BoatMap.get(col).get(2);
        img = BoatImg.get(col);
    }

}
