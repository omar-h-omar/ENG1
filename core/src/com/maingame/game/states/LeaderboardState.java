package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LeaderboardState extends State{
    private final Texture background;
    private List<Boat> boatsInOrder;
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),false); // a font to draw text
    private Boat player;
    private int leg;

    public LeaderboardState(GameStateManager gsm, int leg, List<Boat> boats, Boat player) {
        super(gsm);
        background = new Texture("background.PNG");
        this.player = player;
        this.leg = leg;
        buildBoatsInOrder(boats);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            moveToNewState(leg);
        }
        if(Gdx.input.justTouched()) {
            moveToNewState(leg);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        cam.setToOrtho(false,MainGame.WIDTH,MainGame.HEIGHT);
        sb.setProjectionMatrix(cam.combined);
        sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
        if (leg != 4) {
            font.draw(sb, "Leg " + leg +" Leaderboard",MainGame.WIDTH/2-250, 700);
        }else {
            font.draw(sb, "Final Leaderboard",MainGame.WIDTH/2-250, 700);
        }
        for (int i = 0; i < boatsInOrder.size(); i++){
            sb.draw(boatsInOrder.get(i).images.get(0), MainGame.WIDTH/4 + 100,500 - (i * 120),125,125);
            font.draw(sb,boatsInOrder.get(i).getCumulativeLegTime() + "s",MainGame.WIDTH/2 + 100,600  - (i * 120));
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
    }

    private void buildBoatsInOrder(List<Boat> boats) {
        List<Boat> newList = new ArrayList<>(boats);
        newList.add(player);
        boatsInOrder = new ArrayList<>();
        while (!newList.isEmpty()) {
            Boat highest = null;
            for (Boat boat:newList) {
                if (highest == null) {
                    if (boat.isHasNotLost()) {
                        highest = boat;
                    }
                }
                if (boat.isHasNotLost() && boat.getCumulativeLegTime() < highest.getCumulativeLegTime()) {
                    highest = boat;
                }
            }
            boatsInOrder.add(boatsInOrder.size(),highest);
            newList.remove(highest);
        }
    }

    private void moveToNewState(int leg) {
        if (leg == 4) {
            gsm.set(new WelcomeState(gsm));
        }else if (leg == 3) {
            boatsInOrder = boatsInOrder.subList(0,3);
            boolean gameOver = true;
            for (Boat boat: boatsInOrder) {
                if (boat == player) {
                    gameOver = false;
                }
            }
            if (gameOver) {
                gsm.set(new GameOverSpeed(gsm));
            }else {
                for (Boat boat:boatsInOrder) {
                    boat.setPosY(0);
                    boat.setHealth(100);
                    boat.setFatigue(300);
                    boat.setTotalLegTime(0);
                }
                boatsInOrder.remove(player);
                gsm.set(new PlayState(gsm,boatsInOrder,player,leg + 1));
            }
        }else {
            for (Boat boat:boatsInOrder) {
                boat.setPosY(0);
                boat.setHealth(100);
                boat.setFatigue(300);
                boat.setTotalLegTime(0);
            }
            boatsInOrder.remove(player);
            gsm.set(new PlayState(gsm,boatsInOrder,player,leg + 1));
        }
    }
}
