
package model.board;

/**
 * A factory for creating squares on a chess board
 */
public interface SquareFactory extends Iterable<Square> {
	
	/**
	 * Create a new square
	 * @param file The file of the square
	 * @param rank The rank of the square
	 * @return the new square
	 * @require isSquare(file, rank)
	 */
	public abstract Square create (int file, int rank);
	
	/**
	 * Check whether (file, rank) represents a square on the chess board 
	 * @param file The x-coordinate, may be any integer
	 * @param rank The y-coordinate, may be any integer
	 * @return true if file in [1..MAX_FILE] and rank in [1..MAX_RANK]
	 */
	public abstract boolean isSquare(int file, int rank);
	
}
