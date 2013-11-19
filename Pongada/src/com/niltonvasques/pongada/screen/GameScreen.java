package com.niltonvasques.pongada.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;
import com.niltonvasques.pongada.PongadaGame;
import com.niltonvasques.pongada.controller.BoardController;
import com.niltonvasques.pongada.model.Board;
import com.niltonvasques.pongada.service.ImageLoader;
import com.niltonvasques.pongada.view.BoardRenderer;
import com.niltonvasques.pongada.view.MenuRenderer;
import com.niltonvasques.pongada.view.MenuRenderer.MenuListener;

public class GameScreen implements Screen, InputProcessor{
	private PongadaGame game;
	
	private MenuRenderer menuRenderer;
	private BoardRenderer boardRenderer;
	private BoardController boardController;
	private Board board;
	private boolean menuOn = false;
	
	public GameScreen(PongadaGame game) {
		this.game = game;
		ImageLoader.getInstance().loadImages();
		
		menuRenderer = new MenuRenderer();
		boardRenderer = new BoardRenderer(board = new Board());
//		boardRenderer.setDebug(true);
		boardController = new BoardController(board);
		Gdx.input.setInputProcessor(this);		
		
		menuRenderer.setMenuListener(new MenuListener() {
			
			@Override
			public void onNewGame() {
				closeMenu();
				board.resetBoard();
			}
			
			@Override
			public void onExit() {
				Gdx.app.exit();
			}

			@Override
			public void onResume() {
				closeMenu();				
			}
		});
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		boardRenderer.render(delta);
		if(menuOn) menuRenderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		boardRenderer.dispose();		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.ENTER || keycode == Keys.MENU){
			openMenu(true);
		}		
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	private Vector3 windowTouchPosition = new Vector3();
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		windowTouchPosition.x = screenX;
		windowTouchPosition.y = screenY;
		windowTouchPosition.z = 0;		
		boardRenderer.getCamera().unproject(windowTouchPosition);
		boardController.startDragPiece(windowTouchPosition.x, windowTouchPosition.y);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		boardController.finishDragPiece();
		
		if(board.checkWinner()){
			System.out.println(board.getLastWinner());
			openMenu(false);			
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		windowTouchPosition.x = screenX;
		windowTouchPosition.y = screenY;
		windowTouchPosition.z = 0;		
		boardRenderer.getCamera().unproject(windowTouchPosition);
		boardController.dragPiece(windowTouchPosition.x, windowTouchPosition.y);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private void closeMenu() {
		menuOn = false;
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(false);
	}

	private void openMenu(boolean catchBack) {
		menuOn = true;
		Gdx.input.setInputProcessor(menuRenderer.getMenuStage());
		Gdx.input.setCatchBackKey(catchBack);
	}

}
