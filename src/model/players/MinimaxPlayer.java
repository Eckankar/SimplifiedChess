package model.players;

import java.util.PriorityQueue;

import model.Game;
import model.GameManager;
import model.board.Move;
import model.players.minimax.*;
import model.players.minimax.estimators.PositionBoardEstimator;

/**
 * A computer opponent that uses a simple Minimax algorithm.
 * (http://en.wikipedia.org/wiki/Minimax)
 * @author Sebastian Paaske Tørholm
 */
public class MinimaxPlayer extends AbstractPlayer {
	private static final int STANDARD_SEARCH_DEPTH = 4;
	private Minimax myMinimax;
	
	/**
	 * Creates a new MinimaxPlayer.
	 * @param name name of the player
	 * @param searchDepth search depth to use
	 */
	public MinimaxPlayer(String name, int searchDepth) {
		super(name);
		this.myMinimax = new MinimaxWithAlphaBeta(new PositionBoardEstimator(), searchDepth);
	}
	/**
	 * Creates a new MinimaxPlayer with the default search depth.
	 * @param name name of the player
	 */
	public MinimaxPlayer(String name) { this(name, STANDARD_SEARCH_DEPTH); }
	/**
	 * Creates a new MinimaxPlayer with the default name.
	 * @param searchDepth search depth to use
	 */
	public MinimaxPlayer(int searchDepth) { this("MinimaxPlayer", searchDepth); }
	/**
	 * Creates a new MinimaxPlayer with the default name and search depth.
	 */
	public MinimaxPlayer() { this("MinimaxPlayer", STANDARD_SEARCH_DEPTH); }

	@Override
	public void takeTurn(GameManager gameManager) {
		Game game = gameManager.game;
		PriorityQueue<Move> moveQueue = myMinimax.moveRanking(game.position(), game.toMove());
		
		gameManager.execute(moveQueue.peek());
	}

}
