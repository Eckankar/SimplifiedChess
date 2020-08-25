package model.players;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import model.GameManager;
import model.board.*;
import model.pieces.*;

/**
 * Moves randomly, but still attempts to protect his king from harm.
 * @author Sebastian Paaske Tørholm
 */
public class CleverRandomPlayer extends AbstractPlayer {

	private Random random;
	
	/**
	 * Create a new CleverRandomPlayer.
	 * @param name name of player
	 * @param random random number generator to use
	 */
	public CleverRandomPlayer(String name, Random random) {
		super(name);
		this.random = random;
	}
	
	/**
	 * Create a new CleverRandomPlayer.
	 * @param random random number generator to use
	 */
	public CleverRandomPlayer(Random random) { this("CleverRandomPlayer", random); }
	/**
	 * Create a new CleverRandomPlayer.
	 * @param name name of player
	 */
	public CleverRandomPlayer(String name) { this(name, new Random()); }
	/**
	 * Creates a new CleverRandomPlayer named "CleverRandomPlayer".
	 */
	public CleverRandomPlayer() { this("CleverRandomPlayer", new Random()); }

	@Override
	public void takeTurn(GameManager gameManager) {
		SquareFactory sqFactory = new IntSquareFactory();
		List<Move> allMoves = gameManager.game.allValidMoves();
		UpdatablePosition position = new ArrayPosition(gameManager.game.position());
		PieceColor color = gameManager.game.toMove();
		List<Move> legalMoves = new LinkedList<Move>(); // moves that won't let the 
		                                                // other player kill us next turn
		
		moveLoop: for (Move move : allMoves) {
			ArrayPosition pos = (ArrayPosition)position.deepClone(); // clone it so we can try a move
			pos.get(move.from).moveTo(pos, move.to);
			for (Square sq : sqFactory) {
				Piece p = pos.get(sq);
				if ((p == null) || (p.color() == color))
					continue;
				
				Collection<Square> canTake = p.validSquares(pos);
				for (Square target : canTake) {
					Piece targetPiece = pos.get(target);
					if (targetPiece != null
					&&	targetPiece.color() == color 
				    &&  targetPiece.type() == PieceType.KING) // oh shit, they can take our king
						continue moveLoop;                    // won't make that move!
				}
					
			}
			// At this point, we know that making this move doesn't jeopardize our king next turn
			legalMoves.add(move);
		}
		
		if (legalMoves.size() > 0)
			gameManager.execute(
					legalMoves.get(
						random.nextInt( legalMoves.size() )	
					)
			);
		else
			gameManager.execute(
					allMoves.get(
						random.nextInt( allMoves.size() )	
					)
			);
		
	}
}
