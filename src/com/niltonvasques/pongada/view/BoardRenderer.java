package com.niltonvasques.pongada.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.niltonvasques.pongada.model.Block;
import com.niltonvasques.pongada.model.Board;
import com.niltonvasques.pongada.model.Piece;
import com.niltonvasques.pongada.model.Piece.PieceColor;
import com.niltonvasques.pongada.model.Piece.State;
import com.niltonvasques.pongada.service.ImageLoader;

public class BoardRenderer implements Disposable{

	private static final float CAMERA_WIDTH = 7f*1.6f;
	private static final float CAMERA_HEIGHT = 7f;
	
	private float ppuX;	
	private float ppuY;

	private OrthographicCamera camera;
	
	private Board board;
	
	private SpriteBatch batch;
	
	private TextureAtlas textureAtlas;
	private Texture boardTexture;
	private TextureRegion boardTextureRegion;
	private Sprite boardSprite;
	private AtlasRegion blackPieceIdleTextureRegion;
	private AtlasRegion whitePieceIdleTextureRegion;
	private AtlasRegion blackPieceSelectedTextureRegion;
	private AtlasRegion whitePieceSelectedTextureRegion;	
	private BitmapFont font;
	private SpriteBatch fontBatch;
	
	private ArrayMap<Piece, Sprite> piecesSpriteMap = new ArrayMap<Piece, Sprite>();
	
	/** for debug rendering **/
	private ShapeRenderer debugRenderer = new ShapeRenderer();
	private boolean debug = false;
	
	public BoardRenderer(Board board_) {
		board = board_;
		
		camera = new OrthographicCamera(CAMERA_WIDTH,CAMERA_HEIGHT);
		camera.position.set(CAMERA_WIDTH/2f, CAMERA_HEIGHT/2f, 0);
		camera.update();
		
		batch = new SpriteBatch();
		boardTexture = new Texture("data/board.jpg");
		boardTextureRegion = new TextureRegion(boardTexture, 0, 0, 800, 480);
		
		font = new BitmapFont(Gdx.files.internal("data/ayear-font.fnt"),false);
		font.setColor(Color.WHITE);
		fontBatch = new SpriteBatch();
		
		boardSprite = new Sprite(boardTextureRegion);
		boardSprite.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
		boardSprite.setPosition(0,0);
		
		textureAtlas = ImageLoader.getInstance().getTextureAtlas();
		blackPieceIdleTextureRegion = textureAtlas.findRegion("black-piece-idle");
		whitePieceIdleTextureRegion = textureAtlas.findRegion("white-piece-idle");
		blackPieceSelectedTextureRegion = textureAtlas.findRegion("black-piece-selected");
		whitePieceSelectedTextureRegion = textureAtlas.findRegion("white-piece-selected");
		
		for(Piece p : board.getPieces()){
			TextureRegion tex;
			if(p.getPieceColor() == PieceColor.BLACK)
				tex = blackPieceIdleTextureRegion;
			else
				tex = whitePieceIdleTextureRegion;
			Sprite s = new Sprite(tex);
			s.setSize(p.getBounds().width, p.getBounds().height);
			s.setPosition(p.getBounds().x, p.getBounds().y);
			piecesSpriteMap.put(p, s);
		}
		
		ppuX = Gdx.graphics.getWidth() / CAMERA_WIDTH;
		ppuY = Gdx.graphics.getHeight() / CAMERA_HEIGHT;
		
	}
	
	public void render(float delta){
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		boardSprite.draw(batch);
		
		for(int i = 0; i < piecesSpriteMap.size; i++){
			Piece p = piecesSpriteMap.getKeyAt(i);
			Sprite s = piecesSpriteMap.getValueAt(i);
			
			AtlasRegion tex;
			if(p.getPieceColor() == PieceColor.BLACK)
				if(p.getState() == State.IDLE)
					tex = blackPieceIdleTextureRegion;
				else
					tex = blackPieceSelectedTextureRegion;
			else
				if(p.getState() == State.IDLE)
					tex = whitePieceIdleTextureRegion;
				else
					tex = whitePieceSelectedTextureRegion;
			
			s.setRegion(tex);
			s.setPosition(p.getBounds().x, p.getBounds().y);
			s.draw(batch);
		}	
		
		batch.end();
		
		fontBatch.begin();
		font.draw(fontBatch, board.getBlackWins()+"", 1*ppuX, 6*ppuY);
		font.draw(fontBatch, board.getWhiteWins()+"", 10*ppuX, 6*ppuY);
		fontBatch.end();
		
		if(debug)
			debugRender();
		
	}

	@Override
	public void dispose() {
		textureAtlas.dispose();
		boardTexture.dispose();
		font.dispose();
		batch.dispose();
	}

	public void debugRender() {
			// render blocks
			debugRenderer.setProjectionMatrix(camera.combined);
			debugRenderer.begin(ShapeType.Rectangle);
			for (Block block : board.getBlocks()) {
				Rectangle rect = block.getBounds();
				float x1 = block.getPosition().x + rect.x;
				float y1 = block.getPosition().y + rect.y;
				debugRenderer.setColor(new Color(1, 0, 0, 1));
				debugRenderer.rect(x1, y1, rect.width, rect.height);
			}
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(0,0, 1f, 1f);//Draw Origin
			debugRenderer.end();
	}

	public void setDebug(boolean b) {
		debug = b;		
	}
	
	public OrthographicCamera getCamera(){
		return camera;
	}

}
