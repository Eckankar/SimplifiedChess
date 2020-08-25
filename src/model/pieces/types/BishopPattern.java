package model.pieces.types;

import model.board.Square;

/**
 * Implements the MovementPattern of a standard chess bishop.
 * @author Sebastian Paaske Tørholm
 */
public class BishopPattern extends LinePattern {

	public BishopPattern() {
		super(new int[][] { {  Square.MAX_FILE_RANK,  Square.MAX_FILE_RANK },
			      			{ -Square.MAX_FILE_RANK,  Square.MAX_FILE_RANK },
			      			{  Square.MAX_FILE_RANK, -Square.MAX_FILE_RANK },
			      			{ -Square.MAX_FILE_RANK, -Square.MAX_FILE_RANK } });
	}
}
