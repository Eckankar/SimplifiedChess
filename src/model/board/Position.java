package model.board;
import model.pieces.Piece;

/**
 * A game position, modeled as an immutable mapping from squares to pieces
 */
public interface Position {
	
	/**
	 * The piece at a given location (square)
	 * @param square The location
	 * @return the piece at that location, null if there is no piece at that location
	 */
	abstract public Piece get (Square square);
	
	/**
	 * The piece at a given location (square) given by file and rank
	 * @param file The file (x-coordinate) of the location
	 * @param rank The rank (y-coordinate) of the location
	 * @return the piece at that location, null if there is no piece at that location
	 */
	abstract public Piece get (int file, int rank);
	
	/**
	 * Returns a deep clone of the position.
	 * That it, it returns a Position of the same kind with equivalent pieces placed on it.
	 * @return a deep clone
	 */
	abstract public Position deepClone(); 
}
