package view;

import javax.swing.JToggleButton;

import model.board.Square;

/**
 * A chess square button, representing a single square of a chess board,
 * part of a chess board panel
 * @author henglein
 *
 */
public class ChessSquareButton extends JToggleButton {
	
	private static final long serialVersionUID = 9112464051722707214L;
	private final Square square;
	
	/**
	 * The location (square) of the button on the chess board it is placed on
	 * @return the square of the button on the chess board it is placed on
	 */
	public Square square() {
		return square;
	}

	public ChessSquareButton(Square square) {
		super();
		this.square = square;
	}

}
