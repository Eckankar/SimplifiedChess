package model;


import java.util.List;

import model.board.*;
import model.pieces.PieceColor;

/**
 * A chess-like game
 * @author henglein
 *
 */
public interface Game {

	/**
	 * The current game position 
	 * @return the current game position
	 */
	public abstract Position position();

	/**
	 * Is move legal in current game position?
	 * @param move The move checked for legality, must be nonnull
	 * @return true iff move is legal in present game position?
	 */
	public abstract boolean isLegal(Move move);

	/** 
	 * Execute a move
	 * @param move The move to be executed
	 * @require Game must not be over and move must be legal, move must be nonnull
	 */
	public abstract void execute(Move move);

	/**
	 * Whose turn it is
	 * @return PieceColor.WHITE if it is White's turn, PieceColor.BLACK otherwise
	 */
	public abstract PieceColor toMove();

	/**
	 * Who won?
	 * @return the winner (WHITE or BLACK) or null, if remis (undecided)
	 * @require this.gameOver()
	 */
	public abstract PieceColor winner();

	/**
	 * Is the game over?
	 * @return true iff game is over
	 */
	public abstract boolean gameOver();

	/**
	 * All the valid moves in the current position
	 * @return list of all valid moves in current position
	 */
	public abstract List<Move> allValidMoves();
	
	/**
	 * Last move played
	 * @return the last move played, null if no move has been played yet
	 */
	public abstract DetailedMove lastMove();
	
	/**
	 * The number of moves played so far
	 * @return the number of moves played so far
	 */
    public abstract int numMoves();
    
}