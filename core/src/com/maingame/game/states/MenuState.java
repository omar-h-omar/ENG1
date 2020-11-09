package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;

import java.util.ArrayList;
import java.util.List;

public class MenuState extends State {
	private final Texture background;
	private final Texture leftArrow;
	private final Texture rightArrow;
	private final Texture speed;
	private final Texture robustness;
	private final Texture maneuverability;
	private final Texture acceleration;
	private List<Boat> boats = new ArrayList<Boat>();
	private int x; // current boat index
	private Rectangle rightBounds; // a rectangle around the right arrow.
	private Rectangle leftBounds; // a rectangle around the left arrow.
	private BitmapFont font = new BitmapFont(); // a font to draw text
//	TODO: Finish font to draw text
	public MenuState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.PNG");
		leftArrow = new Texture("leftArrow.png");
		rightArrow = new Texture("rightArrow.png");
		speed = new Texture("speed.png");
		robustness = new Texture("robustness.png");
		maneuverability = new Texture("maneuverability.png");
		acceleration = new Texture("acceleration.png");
		font.getData().setScale(200);
		this.buildBoats();
		rightBounds = new Rectangle((MainGame.WIDTH/2) + 50,MainGame.HEIGHT/2 + 50,200,200);
		leftBounds = new Rectangle((MainGame.WIDTH/2) - 250,MainGame.HEIGHT/2 + 50,200,200);
		x = 0;
	}

//	Initialises and adds boats to the boats list.
	private void buildBoats() {
		boats.add(new Boat("red"));
		boats.add(new Boat("pink"));
		boats.add(new Boat("blue"));
		boats.add(new Boat("yellow"));
		boats.add(new Boat("orange"));
		boats.add(new Boat("green"));
		boats.add(new Boat("purple"));
	}

	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			if (rightBounds.contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
				x+= 1;
			}else if (leftBounds.contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
				x -= 1;
			}
		}
		if (x < 0) {
			x = 0;
		}else if (x > boats.size() - 1){
			x = boats.size() - 1;
		}
	}
	
	@Override
	public void update(float dt) {
		handleInput();
	}
//	TODO: Draw the values for each attribute
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
		Texture img = new Texture(boats.get(x).img);
		sb.draw(img,(MainGame.WIDTH/2) - 100,MainGame.HEIGHT/2 + 50,200,200);
		sb.draw(rightArrow,(MainGame.WIDTH/2) + 50 ,MainGame.HEIGHT/2 + 50,200,200);
		sb.draw(leftArrow,(MainGame.WIDTH/2) - 250 ,MainGame.HEIGHT/2 + 50,200,200);
		sb.draw(speed,100,250,speed.getWidth()/5,speed.getHeight()/5);
		font.draw(sb,"test",0,0);
		sb.draw(acceleration,100,200,speed.getWidth()/5,speed.getHeight()/5);
		sb.draw(robustness,100,150,speed.getWidth()/5,speed.getHeight()/5);
		sb.draw(maneuverability,100,100,speed.getWidth()/5,speed.getHeight()/5);
		sb.end();
	}

	@Override
	public void dispose() {
		background.dispose();
		rightArrow.dispose();
		leftArrow.dispose();
		speed.dispose();
		acceleration.dispose();
		robustness.dispose();
		maneuverability.dispose();
		font.dispose();
	}
}

