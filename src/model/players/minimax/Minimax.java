package model.players.minimax;

import java.util.PriorityQueue;

import model.board.Move;
import model.board.Position;
import model.pieces.PieceColor;

/**
 * Defines a Minimax implementation.
 * @author Sebastian Paaske Tørholm
 */
public interface Minimax {
	/**
	 * Ranks all possible moves after which one has the minimal maximum loss.
	 * @param board board position
	 * @param toMove player to move
	 * @return an ordered queue of moves with the best one ranked first
	 */
	public abstract PriorityQueue<Move> moveRanking(Position board,
			final PieceColor toMove);

}