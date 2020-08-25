package model.players.minimax;

import model.board.*;
import model.pieces.*;
import model.players.minimax.estimators.BoardEstimator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implementation of naïve Minimax.
 * http://en.wikipedia.org/wiki/Minimax
 * @author Sebastian Paaske Tørholm
 */
public class NaiveMinimax implements Minimax {
	private static SquareFactory sqFactory = new IntSquareFactory();
	private BoardEstimator estimator;
	private int depth;
	
	/**
	 * Creates a new Minimax.
	 * @param estimator estimator for board positions
	 * @param depth depth to iterate
	 */
	public NaiveMinimax(BoardEstimator estimator, int depth) {
		this.estimator = estimator;
		this.depth = depth;
	}
	
	public PriorityQueue<Move> moveRanking(Position board, final PieceColor toMove) {
		final ArrayPosition position = new ArrayPosition(board);
		
		Comparator<Move> comparator = new Comparator<Move>() {
			Map<Move, Integer> moveRanking = new HashMap<Move, Integer>();;
			
			@Override
			public int compare(Move o1, Move o2) {
				if (!moveRanking.containsKey(o1)) {
					ArrayPosition posCopy = (ArrayPosition)(position.deepClone());
					Piece p = posCopy.get(o1.from);
					p.moveTo(posCopy, o1.to);
					moveRanking.put(o1, minimax(posCopy, depth-1, toMove.other()));
				}
				if (!moveRanking.containsKey(o2)) {
					ArrayPosition posCopy = (ArrayPosition)(position.deepClone());
					Piece p = posCopy.get(o2.from);
					p.moveTo(posCopy, o2.to);
					moveRanking.put(o2, minimax(posCopy, depth-1, toMove.other()));
				}
				
				int r1 = moveRanking.get(o1),
				    r2 = moveRanking.get(o2);
				
				return r1 > r2 ?  1 :
					   r1 < r2 ? -1 :
						   		  0;
			}		
		};
		
		PriorityQueue<Move> moveRanking = new PriorityQueue<Move>(100, comparator);
		
		for (Square sq : sqFactory) {
			Piece p = position.get(sq);
			if ((p == null) || (p.color() != toMove))
				continue;
			
			for (Square target : p.validSquares(position)) {
				Move m = new Move(sq, target);
				moveRanking.add(m);
			}
		}
		
		return moveRanking;
	}
	
	/**
	 * Minimax implementation.
	 * http://en.wikipedia.org/wiki/Minimax
	 * @param board board position to work with
	 * @param depthLeft depth left to iterate
	 * @param toMove whose turn it is
	 * @return best possible value according to the algorithm
	 */
	private int minimax(Position board, int depthLeft, PieceColor toMove) {
		if (depthLeft <= 0)
			return (toMove == PieceColor.BLACK ? -1 : 1) *
					estimator.boardEstimate(board);
		
		ArrayPosition position = new ArrayPosition(board);
		int bestValue = Integer.MIN_VALUE;
		
		for (Square sq : sqFactory) {
			Piece p = position.get(sq);
			if ((p == null) || (p.color() != toMove))
				continue;
			
			for (Square target : p.validSquares(position)) {
				ArrayPosition posClone = (ArrayPosition)position.deepClone();
				p.moveTo(posClone, target);
				bestValue = Math.max(bestValue, -minimax(posClone, depthLeft-1, toMove.other()));
			}
		}
		
		return bestValue;
	}
}
