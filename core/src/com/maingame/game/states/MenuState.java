package com.maingame.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;

public class MenuState extends State {
	private Texture background;
	private Texture playBtn;

	protected MenuState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.PNG");
		playBtn = new Texture("play.PNG");
	}

	@Override
	protected void handleInput() {
		
	}
	
	@Override
	public void update(float dt) {
		
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
		sb.draw(playBtn, (MainGame.WIDTH / 2) - (playBtn.getWidth() / 2), MainGame.HEIGHT / 2);
		sb.end();
	}

	@Override
	public void dispose() {
		
	}
}

