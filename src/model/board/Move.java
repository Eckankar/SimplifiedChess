package model.board;


/**
 * A move in a chess game, implemented as a pair of squares.
 */
public class Move {
	
	public final Square from, to;
	
	/**
	 * Create a move
	 * @param from The original location (square) of the move, must be nonnull
	 * @param to The new location (square) of the move, must be nonnull and different from from-square
	 */
	public Move(Square from, Square to) {
		assert from != null && to != null : "Squares must be nonnull"; 
		assert !from.equals(to) : "Squares must be different";
		this.from = from;
		this.to = to;
	}
	
	public String toString() {
		return from.toString() + to.toString();
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Move) {
			this.toString().equals(obj.toString());
		}
		return false;
	}
	
	
}
