/**
 * Updatable position
 */
package model.board;

import model.pieces.Piece;

/**
 * A Position that can be updated
 *
 */
public interface UpdatablePosition extends Position {
	
	/**
	 * Place piece at a certain location (square)
	 * @param square The location
	 * @param piece The piece
	 */
	public abstract void set (Square square, Piece piece);
	
	/**
	 * Place piece at a certain location, given by file and rank coordinates
	 * @param file The file (x-coordinate) of the location
	 * @param rank The rank (y-coordinate) of the location
	 * @param piece The piece
	 */
	public abstract void set (int file, int rank, Piece piece);
	
}
