package model.pieces;


import java.util.*;

import model.board.Square;
import model.board.Position;
import model.board.UpdatablePosition;

/**
 * A (chess) piece in simplified chess
 */
public interface Piece extends Cloneable {
	
   /**
	 * The location (square) of this piece
	 * @return the square 
	 */
	public abstract Square location();
	
	/**
	 * Whether this piece can move to a certain square
	 * @param gamePosition The game position (mapping of squares to pieces)
	 * @param square The square to move to, may be null
	 * @return true if this piece can move to square
	 * @require gamePosition.get(this.location()) == this
	 */
	public abstract boolean canMoveTo (Position gamePosition, Square square);
	
	/**
	 * The squares this piece can move to
	 * @param gamePosition The game position
	 * @return the set of all squares this piece can move to
	 * @ensure result is the set of all squares f such that canMoveTo(gamePosition, f)
	 */
	public abstract Collection<Square> validSquares (Position gamePosition);
	
	/**
	 * Move to new square in given position
	 * @param gamePosition The game position
	 * @param square New square
	 * @require canMoveTo(gamePosition, square)
	 * @ensure gamePosition.get(this.location()) == this
	 * @ensure this.location() == square
	 */
    public abstract void moveTo (UpdatablePosition gamePosition, Square square);
    
    /**
     * The color of this piece
     * @return the color of this piece
     */
    public abstract PieceColor color();
    
    /**
     * The type of piece
     * @return the type of this piece
     */
	public abstract PieceType type();
	
    /**
     * Change the type of the piece.
     * @ensure this.type() == type
     */
	public abstract void type(PieceType type);
	
	/**
	 * Clones this piece.
	 * @return a clone of this piece
	 * @ensure result.location() == this.location()
	 * @ensure result.type() == this.type()
	 * @ensure result.color() == this.color()
	 */
	public Object clone();
}
