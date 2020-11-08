package com.maingame.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;

import java.util.ArrayList;
import java.util.List;

public class MenuState extends State {
	private Texture background;
	private List<Boat> boats = new ArrayList<Boat>();;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.PNG");
		this.buildBoats();
	}

	private void buildBoats() {
		boats.add(new Boat("red"));
	}

	@Override
	public void handleInput() {
		
	}
	
	@Override
	public void update(float dt) {
		
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
		Texture img = new Texture(boats.get(0).img);
		sb.draw(img,20,20);
//		sb.draw(playBtn, (MainGame.WIDTH / 2) - (playBtn.getWidth() / 2), MainGame.HEIGHT / 2);
		sb.end();
	}

	@Override
	public void dispose() {
		
	}
}

