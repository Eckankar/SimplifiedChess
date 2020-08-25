package model.pieces.types;

/**
 * The simplified chess king movement pattern.
 * @author Sebastian Paaske Tørholm
 */
public class SimplifiedKingPattern extends JumpingPattern {
	@Override
	protected final int[][] jumpOffsets() {
		return new int[][] { {1, 0}, {0, 1}, {-1, 0}, {0, -1},
		                     {1, 1}, {-1, 1}, {1, -1}, {-1, -1} };
	}

}
