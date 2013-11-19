package com.niltonvasques.pongada.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Piece {
	
	private static final float SIZE = 1f;
	
	public enum State{
		IDLE,DRAGGING
	}
	
	public enum PieceColor{
		BLACK, WHITE
	}
	
	private Vector2 boardPosition; // Describes the piece position on board.
	private Rectangle bounds = new Rectangle();
	private State state = State.IDLE;
	private PieceColor color; 
	
	public Piece( Vector2 screenPos, Vector2 boardPos, PieceColor color_) {
		boardPosition = boardPos;
		color = color_;
		bounds.x = screenPos.x;
		bounds.y = screenPos.y;
		bounds.width = SIZE;
		bounds.height = SIZE;
	}	

	public Vector2 getBoardPosition() {
		return boardPosition;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
	
	public PieceColor getPieceColor(){
		return color;
	}
	
	public void setPieceColor(PieceColor c){
		color = c;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
}
