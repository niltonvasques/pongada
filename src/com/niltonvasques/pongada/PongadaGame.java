package com.niltonvasques.pongada;

import com.badlogic.gdx.Game;
import com.niltonvasques.pongada.screen.GameScreen;

public class PongadaGame extends Game {
	
	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		super.render();		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
