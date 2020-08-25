package model;


import java.util.*;

import model.board.Move;
import model.pieces.*;
import model.players.*;

/**
 * GameManager: Knows a chess game and up to two computer players, makes 
 * moves in the game and informs observers of whenever a move has been made.
 */
public class GameManager extends Observable {

	private Player white, black;
	public final Game game;
	
	public GameManager(Game game) {
		assert game != null;
		this.game = game;
	}
	
	/**
	 * Set the players to play the game
	 * @param white The player playing the white pieces, may be null (no computer player)
	 * @param black The player playing the black pieces, may be null (no computer player)
	 */
	public void setPlayers(Player white, Player black) {
		this.white = white;
		this.black = black;
	}
	
	/**
	 * The white computer player
	 * @return computer player for moving the white pieces
	 */
	public Player whitePlayer() {
		return white;
	}
	
	/**
	 * The black computer player
	 * @return computer player for moving the black pieces
	 */
	public Player blackPlayer() {
		return black;
	}
	
	/**
	 * Execute a particular move and notify all observers
	 * @param move The move to be executed, must be legal
	 */
	public void execute(Move move) {
		assert game.isLegal(move): "Illegal move";
		game.execute(move);
		setChanged();
		notifyObservers();
	}

	/**
	 * If there is a computer player set  for the color whose turn it is, ask it to make a move;
	 * if not, do nothing. 
	 */
	public void move() {
		Player player = game.toMove() == PieceColor.WHITE ? white : black;
		if (player != null)
			player.takeTurn(this);
	}
}
