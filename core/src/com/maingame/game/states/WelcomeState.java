package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;

public class WelcomeState extends State {
	private Texture background;
	private Texture playBtn;
	private Texture title;
	

	public WelcomeState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.PNG");
		playBtn = new Texture("play.PNG");
		title = new Texture("title.PNG");
	}

	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			gsm.set(new MenuState(gsm));
			dispose();
		}
	}
	
	@Override
	public void update(float dt) {
		handleInput();
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
		sb.draw(playBtn, (MainGame.WIDTH / 3) - (playBtn.getWidth() / 10), MainGame.HEIGHT / 50);
		sb.draw(title, (MainGame.WIDTH / 5) - (playBtn.getWidth() / 10), MainGame.HEIGHT / 50);
		sb.end();
	}
	

	@Override
	public void dispose() {
		
	}
}

