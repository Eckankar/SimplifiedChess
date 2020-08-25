package model.pieces.types;

import model.board.Square;

/**
 * Defines the MovementPattern for a standard chess rook.
 * @author Sebastian Paaske Tørholm
 */
public class RookPattern extends LinePattern {
	public RookPattern() {
		super(new int[][] { {0, Square.MAX_RANK}, {Square.MAX_FILE, 0},
							{0, -Square.MAX_RANK}, {-Square.MAX_FILE, 0} });
	}

}
