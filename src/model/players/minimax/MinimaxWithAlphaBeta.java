package model.players.minimax;

import model.board.*;
import model.pieces.*;
import model.players.minimax.estimators.BoardEstimator;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implementation of Minimax with alpha-beta pruning.
 * http://en.wikipedia.org/wiki/Minimax
 * http://en.wikipedia.org/wiki/Alpha-beta_pruning
 * @author Sebastian Paaske Tørholm
 */
public class MinimaxWithAlphaBeta implements Minimax {
	private static SquareFactory sqFactory = new IntSquareFactory();
	private BoardEstimator estimator;
	private int depth;
	
	/**
	 * Creates a new Minimax.
	 * @param estimator estimator for board positions
	 * @param depth depth to iterate
	 */
	public MinimaxWithAlphaBeta(BoardEstimator estimator, int depth) {
		this.estimator = estimator;
		this.depth = depth;
	}
	
	public PriorityQueue<Move> moveRanking(Position board, final PieceColor toMove) {
		final ArrayPosition position = new ArrayPosition(board);
		
		Comparator<Move> comparator = new Comparator<Move>() {
			Map<Move, Integer> moveRanking = new HashMap<Move, Integer>();
			
			@Override
			public int compare(Move o1, Move o2) {
				if (!moveRanking.containsKey(o1)) {
					ArrayPosition posCopy = (ArrayPosition)(position.deepClone());
					Piece p = posCopy.get(o1.from);
					p.moveTo(posCopy, o1.to);
					int rank = minimax(posCopy, depth-1, -Integer.MAX_VALUE, Integer.MAX_VALUE, toMove.other());
					//System.out.printf("%s = %d\n", o1.toString(), rank);
					moveRanking.put(o1, rank);
				}
				if (!moveRanking.containsKey(o2)) {
					ArrayPosition posCopy = (ArrayPosition)(position.deepClone());
					Piece p = posCopy.get(o2.from);
					p.moveTo(posCopy, o2.to);
					int rank = minimax(posCopy, depth-1, -Integer.MAX_VALUE, Integer.MAX_VALUE, toMove.other());
					//System.out.printf("%s = %d\n", o2.toString(), rank);
					moveRanking.put(o2, rank);
				}
				
				int r1 = moveRanking.get(o1),
				    r2 = moveRanking.get(o2);
				
				return r1 > r2 ?  1 :
					   r1 < r2 ? -1 :
						   		  0;
			}		
		};
		
		PriorityQueue<Move> moveRanking = new PriorityQueue<Move>(100, comparator);
		
		List<Move> moves = new LinkedList<Move>();
		for (Square sq : sqFactory) {
			Piece p = position.get(sq);
			if ((p == null) || (p.color() != toMove))
				continue;
			
			for (Square target : p.validSquares(position)) {
				Move m = new Move(sq, target);
				moves.add(m);
			}
		}
		
		// Make the next move non-deterministic.
		Collections.shuffle(moves);
		
		for (Move m : moves) {
			moveRanking.add(m);
		}
		
		return moveRanking;
	}
	
	/**
	 * Minimax with alpha-beta pruning implementation.
	 * http://en.wikipedia.org/wiki/Minimax
	 * http://en.wikipedia.org/wiki/Alpha-beta_pruning
	 * @param board board position to work with
	 * @param depthLeft depth left to iterate
	 * @param toMove whose turn it is
	 * @param alpha alpha
	 * @param beta beta
	 * @return best possible value according to the algorithm
	 */
	private int minimax(final Position board, int depthLeft, int alpha, int beta, PieceColor toMove) {
		boolean hadMoves = false;
		EstimatableArrayPosition position = EstimatableArrayPosition.from(board, estimator);
		
		if (depthLeft > 0) {
			miniMaxLoop:
			for (Square sq : sqFactory) {
				Piece p = position.get(sq);
				if ((p == null) || (p.color() != toMove))
					continue;
				
				for (Square target : p.validSquares(position)) {
					hadMoves = true;
					//EstimatableArrayPosition posClone = (EstimatableArrayPosition)position.deepClone();
					EstimatableArrayPosition posClone = position;
					Piece targetP = posClone.get(target);
					Piece newP = posClone.get(sq);
					newP.moveTo(posClone, target);
					alpha = Math.max(alpha, -minimax(posClone, depthLeft-1, -beta, -alpha, toMove.other()));
					newP.moveTo(posClone, sq);
					posClone.set(target, targetP);
					if (beta <= alpha) {
						break miniMaxLoop;
					}
				}
			}
		}
		if (hadMoves) {
			return alpha;
		} else {
			return (toMove == PieceColor.BLACK ? -1 : 1) * position.estimate();
		}
	}
}
