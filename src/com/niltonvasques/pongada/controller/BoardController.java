package com.niltonvasques.pongada.controller;

import com.badlogic.gdx.math.Rectangle;
import com.niltonvasques.pongada.model.Board;
import com.niltonvasques.pongada.model.Piece;
import com.niltonvasques.pongada.model.Piece.PieceColor;
import com.niltonvasques.pongada.model.Piece.State;

public class BoardController {
	
	
	private Board board;
	private boolean isDragging = false;
	private Piece pieceIsDragged = null;
	private Rectangle touch = new Rectangle(0,0,0.01f,0.01f);
	
	public BoardController(Board board_) {
		
		board = board_;
		pieceIsDragged = null;
		
	}
	
	public void startDragPiece(float x, float y){
		if(board.isRoundFinished()) return;
		touch.x = x;
		touch.y = y;
		if(!isDragging){
			for (Piece p : board.getPieces()) {				
				if(touch.overlaps(p.getBounds())){
					//If player try move another player piece do nothing...
					if(p.getPieceColor() != board.getCurrentColorMove()) return;
					pieceIsDragged = p;
					pieceIsDragged.setState(State.DRAGGING);
					isDragging = true;
					break;
				}
			}
		}
	}
	
	public void dragPiece(float x, float y){
		if(isDragging){
			pieceIsDragged.getBounds().x = x - pieceIsDragged.getBounds().width/2f;
			pieceIsDragged.getBounds().y = y - pieceIsDragged.getBounds().height/2f;
		}
	}
	
	public void finishDragPiece(){
		if(!isDragging) return;
		int newX = Math.round(pieceIsDragged.getBounds().x);
		int newY = Math.round(pieceIsDragged.getBounds().y);
		
		if(!board.tryMove(pieceIsDragged, (int)touch.x, (int)touch.y, newX, newY)){
			pieceIsDragged.getBounds().x = (int)touch.x;
			pieceIsDragged.getBounds().y = (int)touch.y;			
			System.out.println("Invalid Movement");
		}
		pieceIsDragged.setState(State.IDLE);
		isDragging = false;
		
	}
	
}
