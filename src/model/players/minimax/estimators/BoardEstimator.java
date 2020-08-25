package model.players.minimax.estimators;

import model.board.Position;
import model.board.Square;
import model.pieces.Piece;

/**
 * Estimates the current situation of the board.
 * @author Sebastian Paaske Tørholm
 */
public interface BoardEstimator {
	/**
	 * Gives an estimate of the current board.
	 * Negative number = black advantage
 	 * Positive number = white advantage
	 * @param board board to evaluate
	 * @return estimate
	 * @require board != null
	 */
	public int boardEstimate(Position board);
	
	/**
	 * Gives an estimate on how much the value of the board changes by placing
	 * a piece at a given position.
	 * @param pos board to evaluate
	 * @param square square to place on
	 * @param piece piece to place
	 */
	public int estimateChange(Position pos, Square square, Piece piece);

	/**
	 * Gives an estimate on how much the value of the board changes by placing
	 * a piece at a given position.
	 * @param pos board to evaluate
	 * @param file file of piece
	 * @param rank rank of piece
	 * @param piece piece to place
	 */
	public int estimateChange(Position pos, int file, int rank, Piece piece);
}
