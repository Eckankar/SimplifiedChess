package model.pieces;


import java.util.*;

import model.board.Square;
import model.board.SquareFactory;
import model.board.IntSquareFactory;
import model.board.Position;
import model.board.UpdatablePosition;
import model.pieces.types.MovementPattern;

/**
 * An flexible implementation of Piece
 * @author Sebastian Paaske Tørholm
 */
public class GenericPiece implements Piece {
	
	private PieceColor color;
	private Square location;
	private PieceType type;
	private MovementPattern movementPattern;
	private SquareFactory squareFactory = new IntSquareFactory(); // can be changed to other factories for producing square objects
	
	public GenericPiece(PieceColor color, Square square, PieceType type) {
		assert color != null; // square may be null
		this.color = color;
		this.location = square;
		this.type(type);
	}

	final public PieceColor color() {
		return color;
	}
	
	final public Square location() {
		return location;
	}

	public PieceType type() {
		return type;
	}
	
	public void type(PieceType type) {
		this.type = type;
		this.movementPattern = type.pattern;
	}
	
	public void moveTo(UpdatablePosition position, Square f) {
		assert canMoveTo(position, f) : "Cannot move to new square in given position";
		if (location != null) position.set(location, null); // remove piece from current square
		if (f != null) position.set(f, this);		  // put piece at new square
		location = f;				  // update location to reflect the move
	}
	
	public Collection<Square> validSquares(Position position) {
		Collection<Square> validsquares = new HashSet<Square>();
		for (int[] move : movementPattern.legalMoves(this, position)) {
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
		
		if (p != null && p.color() == color) // can't move onto own pieces
			return false; 
		
		for (int[] move : movementPattern.legalMoves(this, position)) {
			int fl = file + move[0];
			int ra = rank + move[1];
			if (fl == f && ra == r)
				return true;
		}
		return false;   
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
			    && type().equals(other.type())
			    && location.equals(other.location());
		} 
		return false;
	}
	
	@Override
	public String toString() {
		return "" + color.c + type.c + location;
	}
	
	@Override
	public Object clone() {
		return new GenericPiece(this.color, this.location, this.type);
	}

}
