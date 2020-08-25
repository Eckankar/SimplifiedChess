package model.pieces.types;

import java.util.Arrays;
import java.util.List;

import model.board.Position;
import model.pieces.Piece;

/**
 * MovementPattern used for pieces that can jump to set offsets without 
 * caring what's between where it is and the resulting position. 
 * @author Sebastian Paaske Tørholm
 */
public abstract class JumpingPattern implements MovementPattern {
	/**
	 * @return offsets this pattern can jump to
	 */
	protected abstract int[][] jumpOffsets(); 
	
	@Override
	public List<int[]> legalMoves(Piece piece, Position board) {
		return Arrays.asList(jumpOffsets());
	}

}
