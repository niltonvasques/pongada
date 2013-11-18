package com.niltonvasques.pongada.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ImageLoader {
	
	private TextureAtlas textureAtlas;
	private static ImageLoader mInstance;
	
	private ImageLoader(){
		
	}
	
	public static ImageLoader getInstance(){
		if(mInstance == null){
			mInstance = new ImageLoader();
		}
		return mInstance;
	}
	
	public void loadImages(){
		textureAtlas = new TextureAtlas(Gdx.files.internal("data/pongada.pack"));
	}

	public TextureAtlas getTextureAtlas() {
		return textureAtlas;
	}

}
