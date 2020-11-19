package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;

/**
 * Shows a Game Over Screen for users who have hit too many obstacles and reached 0 health.
 * Users can then retry beating the game by clicking any key or any part of the screen.
 */

public class GameOverSpeed extends State {

	private final Texture background;
	private final Texture gameOverBtn;
	private final Texture info;
	

	public GameOverSpeed(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.PNG");
		gameOverBtn = new Texture("gameover.png");
		info = new Texture("gameoverspeed.png");
	}

	/**
	 * {@inheritDoc}
	 * Moves to the MenuState once any input is provided
	 * @see MenuState
	 */
	@Override
	public void handleInput() {
	
		if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
			gsm.set(new WelcomeState(gsm));
			dispose();
		}
		if(Gdx.input.justTouched()) {
			gsm.set(new WelcomeState(gsm));
			dispose();
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
	 * @param sb a batch for drawing objects
	 */
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
		sb.draw(gameOverBtn, (MainGame.WIDTH / 3) - (gameOverBtn.getWidth() / 10), MainGame.HEIGHT / 4);
		sb.draw(info, (MainGame.WIDTH / 3) - (info.getWidth() / 7), MainGame.HEIGHT / 10);
		sb.end();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		background.dispose();
		gameOverBtn.dispose();
		info.dispose();
	}
}

