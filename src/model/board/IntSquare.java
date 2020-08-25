package model.board;

/**
 * Squares on a chess board with their position stored as one integer.
 * @author Sebastian Paaske Tørholm
 */
public class IntSquare implements Square {
	/**
	 * Returns the integer the given square would be stored as internally.
	 * @param file intended rank for the square
	 * @param rank intended file for the square
	 * @return the position as it would be stored internally
	 * @require new IntSquareFactory().isSquare(file, rank)
	 * @ensure new IntSquare(file, rank).position() == result
	 */
	public static int fileRankToPosition(int file, int rank) {
		assert new IntSquareFactory().isSquare(file, rank);
		return (rank - 1) * Square.MAX_FILE + file - 1;
	}

	private int position;

	/**
	 * Creates an IntSquare based on file and rank.
	 * 
	 * @param file
	 *            intended file for the square (as a letter)
	 * @param rank
	 *            intended rank for the square
	 * @require new IntSquareFactory().isSquare(Character.toLowerCase(file) -
	 *          'a' + 1, rank);
	 * @ensure this.file() = Character.toLowerCase(file) - 'a' + 1
	 * @ensure this.rank() = rank
	 */
	IntSquare(char file, int rank) {
		this(Character.toLowerCase(file) - 'a' + 1, rank);
	}

	/**
	 * Creates an IntSquare based on file and rank.
	 * 
	 * @param file
	 *            intended file for the square
	 * @param rank
	 *            intended rank for the square
	 * @require new IntSquareFactory().isSquare(file, rank);
	 * @ensure this.file() = file
	 * @ensure this.rank() = rank
	 */
	IntSquare(int file, int rank) {
		assert new IntSquareFactory().isSquare(file, rank);
		this.position = fileRankToPosition(file, rank);
	}

	/**
	 * Creates an IntSquare based on a string representation of the tile
	 * position.
	 * 
	 * @param pos
	 *            string containing tile position
	 * @require new IntSquareFactory().isSquare(
	 *          Character.toUpperCase(pos.charAt(0)) - 'a' + 1,
	 *          Integer.parseInt(pos.substring(1)) );
	 * @ensure this.file() = Character.toLowerCase(pos.charAt(0)) - 'a' + 1
	 * @ensure this.rank() = Integer.parseInt(pos.substring(1))
	 */
	IntSquare(String pos) {
		this(pos.charAt(0), Integer.parseInt(pos.substring(1)));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Square) {
			Square other = (Square) obj;
			return file() == other.file() && rank() == other.rank();
		} else
			return false;
	}

	@Override
	public int file() {
		return this.position % Square.MAX_FILE + 1;
	}

	@Override
	public int hashCode() {
		return position; // position is distinct for different squares.
	}
	
	/**
	 * @return the position of this given square as stored internally
	 * @ensure result >= 0
	 */
	public int position() {
		return position;
	}

	@Override
	public int rank() {
		return (int)(this.position / Square.MAX_FILE) + 1;
	}

	@Override
	public String toString() {
		return String.format("%c%d", this.file() + 'a' - 1, this.rank());
	}

}
