package model.players;

import model.*;
import model.board.*;
import java.util.List;
import java.util.Random;

/**
 * Moves completely randomly.
 * @author Sebastian Paaske Tørholm
 */
public class RandomPlayer extends AbstractPlayer {
	private Random random;
	
	/**
	 * Create a new RandomPlayer.
	 * @param name name of player
	 * @param random random number generator to use
	 */
	public RandomPlayer(String name, Random random) {
		super(name);
		this.random = random;
	}
	
	/**
	 * Create a new RandomPlayer.
	 * @param random random number generator to use
	 */
	public RandomPlayer(Random random) { this("RandomPlayer", random); }
	/**
	 * Create a new RandomPlayer.
	 * @param name name of player
	 */
	public RandomPlayer(String name) { this(name, new Random()); }
	/**
	 * Create a new RandomPlayer.
	 */
	public RandomPlayer() { this("RandomPlayer", new Random()); }
	
	@Override
	public void takeTurn(GameManager gameManager) {
		List<Move> allMoves = gameManager.game.allValidMoves();
		
		gameManager.execute(
				allMoves.get(
					random.nextInt( allMoves.size() )	
				)
		);
	}
}
