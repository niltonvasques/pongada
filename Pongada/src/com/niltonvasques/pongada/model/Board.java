package com.niltonvasques.pongada.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.niltonvasques.pongada.model.Piece.PieceColor;

public class Board {
	private final int BLACK_PIECE_NUMBER = 1;
	private final int WHITE_PIECE_NUMBER = 10;
	
	private Array<Piece> pieces = new Array<Piece>();
	private Array<Block> blocks = new Array<Block>();
	private int[][] resultMatrix = new int[3][3];
	private Array<Index>[][] possibleMoves = new Array[3][3];
	private Index[][] matrix = new Index[3][3];	
	
	private PieceColor currentColorMove = PieceColor.WHITE;	
	
	private PieceColor lastWinner = PieceColor.BLACK;
	
	private int whiteWins = 0;
	private int blackWins = 0;
	
	private boolean roundFinished = false;
	
	private class Index{
		
		public int x;
		public int y;
		
		public Index(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public Board() {
		createBoard();
	}

	private void createBoard() {
		pieces.add(new Piece(new Vector2(3,1), new Vector2(2, 0), PieceColor.WHITE));
		pieces.add(new Piece(new Vector2(5,1), new Vector2(2, 1), PieceColor.WHITE));
		pieces.add(new Piece(new Vector2(7,1), new Vector2(2, 2), PieceColor.WHITE));
		
		pieces.add(new Piece(new Vector2(3,5), new Vector2(0, 0), PieceColor.BLACK));
		pieces.add(new Piece(new Vector2(5,5), new Vector2(0, 1), PieceColor.BLACK));
		pieces.add(new Piece(new Vector2(7,5), new Vector2(0, 2), PieceColor.BLACK));
		
		resetBoard();		
		
		for(int i = 0; i < 11; i++)
			for(int j = 0; j < 7; j++)
				blocks.add(new Block(new Vector2(i, j)));
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				matrix[i][j] = new Index(i,j);
		
		possibleMoves[0][0] = new Array<Index>();
		possibleMoves[0][0].add(matrix[0][1]);
		possibleMoves[0][0].add(matrix[1][0]);
		
		possibleMoves[0][1] = new Array<Index>();
		possibleMoves[0][1].add(matrix[0][0]);
		possibleMoves[0][1].add(matrix[0][2]);
		
		possibleMoves[0][2] = new Array<Index>();
		possibleMoves[0][2].add(matrix[0][1]);
		possibleMoves[0][2].add(matrix[1][2]);
		
		possibleMoves[1][0] = new Array<Index>();
		possibleMoves[1][0].add(matrix[0][0]);
		possibleMoves[1][0].add(matrix[2][0]);
		
		possibleMoves[1][1] = new Array<Index>();
		
		possibleMoves[1][2] = new Array<Index>();
		possibleMoves[1][2].add(matrix[0][2]);
		possibleMoves[1][2].add(matrix[2][2]);
		
		possibleMoves[2][0] = new Array<Index>();
		possibleMoves[2][0].add(matrix[1][0]);
		possibleMoves[2][0].add(matrix[2][1]);
		
		possibleMoves[2][1] = new Array<Index>();
		possibleMoves[2][1].add(matrix[2][0]);
		possibleMoves[2][1].add(matrix[2][2]);
		
		possibleMoves[2][2] = new Array<Index>();
		possibleMoves[2][2].add(matrix[1][2]);
		possibleMoves[2][2].add(matrix[2][1]);
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				possibleMoves[i][j].add(matrix[1][1]);
				possibleMoves[1][1].add(matrix[i][j]);
			}
		
	}

	public void resetBoard() {
		
		pieces.get(0).getBounds().x = 3;
		pieces.get(0).getBounds().y = 1;
		pieces.get(0).getBoardPosition().x = 2;
		pieces.get(0).getBoardPosition().y = 0;
		
		pieces.get(1).getBounds().x = 5;
		pieces.get(1).getBounds().y = 1;
		pieces.get(1).getBoardPosition().x = 2;
		pieces.get(1).getBoardPosition().y = 1;
		
		pieces.get(2).getBounds().x = 7;
		pieces.get(2).getBounds().y = 1;
		pieces.get(2).getBoardPosition().x = 2;
		pieces.get(2).getBoardPosition().y = 2;
		
		pieces.get(3).getBounds().x = 3;
		pieces.get(3).getBounds().y = 5;
		pieces.get(3).getBoardPosition().x = 0;
		pieces.get(3).getBoardPosition().y = 0;
		
		pieces.get(4).getBounds().x = 5;
		pieces.get(4).getBounds().y = 5;
		pieces.get(4).getBoardPosition().x = 0;
		pieces.get(4).getBoardPosition().y = 1;
		
		pieces.get(5).getBounds().x = 7;
		pieces.get(5).getBounds().y = 5;
		pieces.get(5).getBoardPosition().x = 0;
		pieces.get(5).getBoardPosition().y = 2;
		
		resultMatrix[0][0] = BLACK_PIECE_NUMBER;
		resultMatrix[0][1] = BLACK_PIECE_NUMBER;
		resultMatrix[0][2] = BLACK_PIECE_NUMBER;
		resultMatrix[1][0] = 0;
		resultMatrix[1][1] = 0;
		resultMatrix[1][2] = 0;
		resultMatrix[2][0] = WHITE_PIECE_NUMBER;
		resultMatrix[2][1] = WHITE_PIECE_NUMBER;
		resultMatrix[2][2] = WHITE_PIECE_NUMBER;
		
		roundFinished = false;
	}
	
	public Array<Piece> getPieces(){
		return pieces;
	}

	public Array<Block> getBlocks() {
		return blocks;
	}
	
	private int boardX2ScreenY(int i){
		return  5-i*2;
	}
	
	private int boardY2ScreenX(int j){
		return j*2+3;
	}
	
	private int screenX2boardY(int x){
		return (x-3)/2;
	}
	
	private int screenY2boardX(int y){
		return (y-5)/2*(-1);
	}
	
	public boolean checkWinner(){
		int whiteSum = WHITE_PIECE_NUMBER * 3;
		if((resultMatrix[0][0] + resultMatrix[0][1] + resultMatrix[0][2]) == whiteSum
			|| (resultMatrix[1][0] + resultMatrix[1][1] + resultMatrix[1][2]) == whiteSum
			|| (resultMatrix[0][0] + resultMatrix[1][0] + resultMatrix[2][0]) == whiteSum 
			|| (resultMatrix[0][1] + resultMatrix[1][1] + resultMatrix[2][1]) == whiteSum
			|| (resultMatrix[0][2] + resultMatrix[1][2] + resultMatrix[2][2]) == whiteSum
			|| (resultMatrix[0][0] + resultMatrix[1][1] + resultMatrix[2][2]) == whiteSum
			|| (resultMatrix[0][2] + resultMatrix[1][1] + resultMatrix[2][0]) == whiteSum){
			 lastWinner = PieceColor.WHITE;
			 currentColorMove = PieceColor.WHITE;
			 whiteWins++;
			 roundFinished = true;
			 return true;
		}
		
		int blackSum = BLACK_PIECE_NUMBER * 3;
		if((resultMatrix[2][0] + resultMatrix[2][1] + resultMatrix[2][2]) == blackSum
				|| (resultMatrix[1][0] + resultMatrix[1][1] + resultMatrix[1][2]) == blackSum
				|| (resultMatrix[0][0] + resultMatrix[1][0] + resultMatrix[2][0]) == blackSum 
				|| (resultMatrix[0][1] + resultMatrix[1][1] + resultMatrix[2][1]) == blackSum
				|| (resultMatrix[0][2] + resultMatrix[1][2] + resultMatrix[2][2]) == blackSum
				|| (resultMatrix[0][0] + resultMatrix[1][1] + resultMatrix[2][2]) == blackSum
				|| (resultMatrix[0][2] + resultMatrix[1][1] + resultMatrix[2][0]) == blackSum){
				 lastWinner = PieceColor.BLACK;
				 currentColorMove = PieceColor.BLACK;
				 blackWins++;
				 roundFinished = true;
				 return true;
			}
		
		return false;
	}
	
	
	public boolean tryMove(Piece p, int originScreenX, int originScreenY, int destScreenX, int destScreenY){
		if(roundFinished) return false;
		int boardOriginX = screenY2boardX(originScreenY);
		int boardOriginY = screenX2boardY(originScreenX);
//		System.out.println("originX: "+originScreenX+" originY: "+originScreenY+" destX: "+destScreenX+
//				" destY: "+destScreenY);
//		System.out.println("boardOriginX: "+boardOriginX+" boardOriginY: "+boardOriginY);
		
		Array<Index> arr = possibleMoves[boardOriginX][boardOriginY];
		
		for (Index v : arr) {
			if( destScreenX == boardY2ScreenX(v.y) && destScreenY == boardX2ScreenY(v.x) &&
					resultMatrix[v.x][v.y] == 0 ){
				// If player move piece to same place return invalid movement
				if(boardOriginX == v.x && boardOriginY == v.y) return false;
				
				p.getBoardPosition().x = v.x;
				p.getBoardPosition().y = v.y;
				p.getBounds().x = destScreenX;
				p.getBounds().y = destScreenY;
				resultMatrix[boardOriginX][boardOriginY] = 0;
				resultMatrix[v.x][v.y] = (p.getPieceColor() == PieceColor.WHITE ? WHITE_PIECE_NUMBER : BLACK_PIECE_NUMBER);
				currentColorMove = (currentColorMove == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK);
				return true;
			}
		}
		return false;
	}

	public PieceColor getLastWinner() {
		return lastWinner;
	}
	
	public boolean isRoundFinished(){
		return roundFinished;
	}
	
	public int getBlackWins(){
		return blackWins;
	}
	
	public int getWhiteWins(){
		return whiteWins;
	}

	public void setCurrentColorMove(PieceColor currentColorMove) {
		this.currentColorMove = currentColorMove;
	}

	public PieceColor getCurrentColorMove() {
		return currentColorMove;
	}
	
	

}
