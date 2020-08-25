package model.pieces.types;

import java.util.List;
import model.board.Position;
import model.pieces.Piece;

/**
 * Defines what movements a piece is allowed to make.
 * @author Sebastian Paaske Tørholm
 */
public interface MovementPattern {
	/**
	 * Get the legal move this MovementPattern provides the piece with in the given position
	 * @param piece our piece
	 * @param board the situation of the board
	 * @return a list of legal moves as coordinate offsets
	 */
	public List<int[]> legalMoves(Piece piece, Position board);
}
