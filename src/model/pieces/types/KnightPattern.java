package model.pieces.types;

/**
 * Implements a standard Chess knight pattern.
 * @author Sebastian Paaske Tørholm
 */
public class KnightPattern extends JumpingPattern {
	@Override
	protected final int[][] jumpOffsets() {
		return new int[][]{ {1, 2}, {-1, 2}, {1, -2}, {-1, -2},
			                {2, 1}, {-2, 1}, {2, -1}, {-2, -1} };
	}
}
