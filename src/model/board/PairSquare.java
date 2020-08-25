package model.board;


/**
 * Squares on a chess board, implemented by a pair of coordinates (file and rank)
 */
public class PairSquare implements Square {

	private final int file;
	private final int rank;
	 
    PairSquare(int file, int rank) {
		assert new PairSquareFactory().isSquare(file, rank);
		this.file = file;
		this.rank = rank;
	}
	
	PairSquare(char c, int rank) {
		this(c - 'a' + 1, rank);
	}
	
    PairSquare(String pos) {
		// assert pos.length() == 2;
		this(pos.charAt(0), pos.charAt(1) - '0');
	}
	
	public int file() {
		return file;
	}

	public int rank() {
		return rank;
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
	public int hashCode() {
		return (file() - 1) * MAX_RANK + (rank() - 1);
	}
	
	@Override
	public String toString() {
		return "" + ((char) ('a' + file() - 1)) + rank();
		// return "" + file + rank;
	}
}
