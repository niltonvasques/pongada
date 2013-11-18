package com.niltonvasques.pongada.view;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.niltonvasques.pongada.service.ImageLoader;

public class MenuRenderer {
	
	public interface MenuListener{
		public void onNewGame();
		public void onExit();
		public void onResume();
	}
	
	private static final float CAMERA_WIDTH = 7f*1.6f;
	private static final float CAMERA_HEIGHT = 7f;

	private OrthographicCamera camera;
	
	private Sprite menuSprite;
	private TextureAtlas textureAtlas;
	private SpriteBatch batch;
	private Image newGameImage;
	private Image exitImage;
	private Stage menuStage;
	private MenuListener mListener;
	
	public MenuRenderer() {
		
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera(CAMERA_WIDTH,CAMERA_HEIGHT);
		camera.position.set(CAMERA_WIDTH/2f, CAMERA_HEIGHT/2f, 0);
		camera.update();
		
		textureAtlas = ImageLoader.getInstance().getTextureAtlas();
		AtlasRegion menuRegion = textureAtlas.findRegion("menu");
		AtlasRegion newGameRegion = textureAtlas.findRegion("new-game");
		AtlasRegion exitRegion = textureAtlas.findRegion("exit");
		
		menuStage = new Stage();
		menuStage.setCamera(camera);
		
		menuSprite = new Sprite(menuRegion);
		menuSprite.setSize(7f, CAMERA_HEIGHT);
		menuSprite.setPosition(2, 0);
		
		newGameImage = new Image(newGameRegion);
		newGameImage.setSize(5f, 2f);
		newGameImage.setPosition(3, 3);
		menuStage.addActor(newGameImage);
		
		
		exitImage = new Image(exitRegion);
		exitImage.setSize(5f, 2f);
		exitImage.setPosition(3, 1);
		menuStage.addActor(exitImage);
		
		newGameImage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(mListener != null) mListener.onNewGame();
			}
		});		
		
		exitImage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(mListener != null) mListener.onExit();
			}
		});
		
		menuStage.addListener(new InputListener(){
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if(keycode == Keys.BACK){
					if(mListener != null) mListener.onResume();
					return true;
				}
				return super.keyUp(event, keycode);
			}
		});
	}
	
	public Stage getMenuStage(){
		return menuStage;
	}
	
	public void setMenuListener(MenuListener m){
		mListener = m;
	}
	
	
	
	public void render(float delta){
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();		
		menuSprite.draw(batch);
		batch.end();
		
		menuStage.act();
		menuStage.draw();
	}

}
