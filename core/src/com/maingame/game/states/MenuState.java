package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Shows a menu where users can select different coloured boats and see their different stats.
 * Shows the users game controls.
 */
public class MenuState extends State {
	private final Texture background;
	private final Texture leftArrow;
	private final Texture rightArrow;
	private final Texture playBtn;
	private final Texture arrows;
	private final Texture wasd;
	private List<Boat> boats = new ArrayList<Boat>();
	private int x; // current boat index
	private final Rectangle rightBounds; // a rectangle around the right arrow used for detecting a click.
	private final Rectangle leftBounds; // a rectangle around the left arrow used for detecting a click.
	private final Rectangle btnBounds;
	private final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),false); // a font to draw text

	public MenuState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.PNG");
		leftArrow = new Texture("leftArrow.png");
		rightArrow = new Texture("rightArrow.png");
		playBtn = new Texture("play.png");
		arrows = new Texture("arrows.png");
		wasd = new Texture("wasd.png");
		this.buildBoats();
		rightBounds = new Rectangle(350,MainGame.HEIGHT/2 + 50,200,200);
		leftBounds = new Rectangle(50,MainGame.HEIGHT/2 + 50,200,200);
		btnBounds = new Rectangle(MainGame.WIDTH - 300 - 100,0,300,300);
		x = 0;
	}

	/**
	 * Initialises and adds boats to the boats list.
	 */
	private void buildBoats() {
		boats.add(new Boat("red"));
		boats.add(new Boat("pink"));
		boats.add(new Boat("blue"));
		boats.add(new Boat("yellow"));
		boats.add(new Boat("orange"));
		boats.add(new Boat("green"));
		boats.add(new Boat("purple"));
	}

	/**
	 * Generates a random list of enemy boats for the PlayState.
	 * @see PlayState#PlayState(GameStateManager, List, Boat, int)
	 * @param player the boat of the player.
	 * @return a random list of enemy boats.
	 */
	private List<Boat> PlayStateBoats(Boat player) {
		List<Boat> output = new ArrayList<Boat>();
		for (int i = 0; i < 4; i++) {
			Random generator = new Random();
			Boat boat = this.boats.get(generator.nextInt(this.boats.size() - 1));
			output.add(boat);
			this.boats.remove(boat);
		}
		return output;
	}

	/**
	 * {@inheritDoc}
	 * Changes the current boat or Shows the PlayState depending on user input
	 */
	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			if (rightBounds.contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
				x+= 1;
			}else if (leftBounds.contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
				x -= 1;
			}else if (btnBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())){
				Boat playerBoat = boats.get(x);
				this.boats.remove(x);
				this.boats = PlayStateBoats(playerBoat);
				gsm.set(new PlayState(gsm,boats, playerBoat,1));
			}
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			x += 1;
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			x -= 1;
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))){
			Boat playerBoat = boats.get(x);
			this.boats.remove(x);
			this.boats = PlayStateBoats(playerBoat);
			gsm.set(new PlayState(gsm,boats, playerBoat,1));
		}
		if (x < 0) {
			x = 0;
		}else if (x > boats.size() - 1){
			x = boats.size() - 1;
		}
	}

	/**
	 * {@inheritDoc}
	 * @param dt the time between each start of a render()
	 */
	@Override
	public void update(float dt) {
		handleInput();
	}

	/**
	 * {@inheritDoc}
	 * Shows the user the current boat selected, its stats, game controls and button to start game.
	 * @param sb a batch for drawing objects
	 */
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
		Texture img = boats.get(x).images.get(0);
		sb.draw(img,200,MainGame.HEIGHT/2 + 50,200,200);
		sb.draw(rightArrow,350 ,MainGame.HEIGHT/2 + 50,200,200);
		sb.draw(leftArrow,50 ,MainGame.HEIGHT/2 + 50,200,200);
		font.draw(sb,"Speed: ",100,300);
		font.draw(sb,"Acceleration: ",100,250);
		font.draw(sb,"Robustness: ",100,200);
		font.draw(sb,"maneuverability: ",100,150);
		font.draw(sb,Integer.toString(boats.get(x).speed),550,300);
		font.draw(sb,Integer.toString(boats.get(x).acceleration),550,250);
		font.draw(sb,Integer.toString(boats.get(x).robustness),550,200);
		font.draw(sb,Integer.toString(boats.get(x).maneuverability),550,150);
		font.draw(sb,"Controls",MainGame.WIDTH - 350,600);
		sb.draw(playBtn, MainGame.WIDTH - 300 - 100, 0, 300,300);
		sb.draw(arrows,MainGame.WIDTH - 325,400,arrows.getWidth()/4,arrows.getHeight()/4);
		sb.draw(wasd,MainGame.WIDTH - 325,250,arrows.getWidth()/4,arrows.getHeight()/4);
		sb.end();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		background.dispose();
		rightArrow.dispose();
		leftArrow.dispose();
		playBtn.dispose();
		arrows.dispose();
		wasd.dispose();
		font.dispose();
	}
}

