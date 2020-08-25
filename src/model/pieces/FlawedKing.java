package model.pieces;


import java.util.*;

import model.board.Square;
import model.board.SquareFactory;
import model.board.PairSquareFactory;
import model.board.Position;
import model.board.UpdatablePosition;

/**
 * An implementation of a king in simplified chess.  
 * I didn't test it though.  So I wonder if it is correct?
 * @author henglein
 */
public class FlawedKing implements Piece {
	
	private PieceColor color;
	private PieceType type;
	private Square location;
	private SquareFactory squareFactory = new PairSquareFactory(); // can be changed to other factories for producing square objects
	
	public FlawedKing(PieceColor color, Square square) {
		assert color != null; // square may be null
		this.color = color;
		this.type = PieceType.KING;
		this.location = square;
	}

	final public PieceColor color() {
		return color;
	}

	final public PieceType type() {
		return type;
	}
	
	final public Square location() {
		return location;
	}

	public void moveTo(UpdatablePosition position, Square f) {
		assert canMoveTo(position, f) : "Cannot move to new square in given position";
		if (location != null) position.set(location, null); // remove piece from current square
		if (f != null) position.set(f, this);		  // put piece at new square
		location = f;				  // update location to reflect the move
	}
	
	private final int[][] kingMoves = {
			new int[] {0,1},
			new int[] {1,1},
			new int[] {1,0},
			new int[] {1,-1},
			new int[] {0,-1},
			new int[] {-1,-1},
			new int[] {-1,0},
			new int[] {-1,-1}
	};
	
	public Collection<Square> validSquares(Position position) {
		Collection<Square> validsquares = new HashSet<Square>();
		for (int[] move : kingMoves) {
			int f = location.file() + move[0];
			int r = location.rank() + move[1];
			if (f >= 1 && f <= Square.MAX_FILE && r >= 1 && r <= Square.MAX_RANK) {
				Piece p = position.get(f, r);
				if (p==null) 
					validsquares.add(squareFactory.create(f, r));
				else if (p.color() != color) 
					validsquares.add(squareFactory.create(f, r));
			}
		}
		return validsquares;
	}
	
	public boolean canMoveTo (Position position, Square square) {
		int f = square.file();
		int r = square.rank();
		int file = location.file();
		int rank = location.rank();
		Piece p = position.get(f, r);
		assert position.get(file, rank) == this;
		return Math.abs(f - file) <= 1 && Math.abs(r - rank) <=1 
		    && p.color() != color;   
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Piece) {
			Piece other = (Piece) obj;
			return color.equals(other.color()) 
			    && type.equals(other.type())
			    && location.equals(other.location());
		} 
		return false;
	}
	
	@Override
	public String toString() {
		return "" + color.c + type.c + location;
	}

	/*-------------------------
	 * The following were added by me because I modified the
	 * interface Piece a bit:
	 *-------------------------*/
	public void type(PieceType type) {
		this.type = type;
	}
	
	public Object clone() {
		return new FlawedKing(this.color, this.location);
	}
}
