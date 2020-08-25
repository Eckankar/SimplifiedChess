/**
 * A GUI controller with an associated GameController.
 * It listens to ChesssquareButton clicks and Play button clicks.
 * It assembles ChesssquareButton clicks into moves to be executed by the GameController
 * and forwards PlayButton clicks as requests to move to the GameController.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.JOptionPane;

import model.GameManager;
import model.board.Square;
import model.board.Move;
import model.pieces.Piece;
import view.ChessSquareButton;


/**
 * A GUI Controller for playing simplified chess
 */
public class ChessGUIController implements GUIController {
	
	private Square from = null;
	private GameManager gameManager;
	
	/**
	 * A new GUI controller
	 * @param gameManager The GameController that 
	 */
	public ChessGUIController(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	/**
	 * Registers ChesssquareButton clicks.  It constructs a Move from a sequence 
	 * of two subsequent ChesssquareButton clicks, where the first one is a click
	 * of a piece in the current position of the player whose turn it is.  If the Move constitutes
	 * a legal move, it asks the associated GameController to execute it.
	 * If the move is illegal, it displays an error message.
	 */
	public void itemStateChanged(ItemEvent e) {
		int buttonState = e.getStateChange();		
		if (buttonState == ItemEvent.SELECTED) {
			// a square has been selected
			if (from == null) {
				// first square selected
				from = ((ChessSquareButton) e.getSource()).square();
				// if no piece is on square or piece is wrong color, abort.
				Piece p = gameManager.game.position().get(from);
				if (p == null || p.color() != gameManager.game.toMove()) {
					((ChessSquareButton) e.getSource()).setSelected(false);
				}
			} else { // from != null 
				// second square selected - make move.
				Square to = ((ChessSquareButton) e.getSource()).square();
				Move move = new Move(from, to);
				if (gameManager.game.isLegal(move))
					gameManager.execute(move);
				else {
					JOptionPane.showMessageDialog(null, "Illegal move", "Try again", JOptionPane.ERROR_MESSAGE);
					from = null;
				}			
			}
		} else {
			// a square has been deselected
			from = null;
		}
	}

	/**
	 * Asks the associated GameController to make a move
	 */
	public void actionPerformed(ActionEvent e) {
		gameManager.move();
	}

}
