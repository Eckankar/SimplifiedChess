package model.players;

import model.GameManager;

/**
 * A computer player.  
 */


public interface Player {
	
	/**
	 * The name of this player
	 * @return the name of this player
	 */
	String name();
	
	/**
	 * Make a move in a game of simplified chess
	 * @param gameManager The game manager that can be queried about the game state 
	 *        and that must be used to execute the move chosen
	 */
	void takeTurn(GameManager gameManager);	
}
