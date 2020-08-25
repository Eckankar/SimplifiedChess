package model.pieces.types;

import java.util.ArrayList;
import java.util.List;

import model.board.Position;
import model.pieces.Piece;

/**
 * Creates a MovementPattern from multiple MovementPatterns.
 * @author Sebastian Paaske Tørholm
 */
public class MultiPattern implements MovementPattern {
	private List<MovementPattern> patterns;
	
	/**
	 * Creates a new MultiPattern.
	 * @param patterns patterns to include in the pattern
	 */
	public MultiPattern(List<MovementPattern> patterns) {
		this.patterns = patterns;
	}
	
	@Override
	public List<int[]> legalMoves(Piece piece, Position board) {
		List<int[]> moves = new ArrayList<int[]>(); 
		for (MovementPattern pattern : patterns)
			moves.addAll(pattern.legalMoves(piece, board));
		return moves;
	}

}
