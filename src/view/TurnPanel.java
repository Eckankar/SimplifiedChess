package view;

import javax.swing.JPanel;

import model.*;
import model.pieces.*;

/**
 * A GUI panel that displays whose turn it is in a game of simplified chess: it paints its area black
 * if it is Black's turn to move; white if it is White's turn.
 */
public class TurnPanel extends JPanel {
	private static final long serialVersionUID = -1639810111439552425L;
	
	public TurnPanel() {
		this.setBackground(java.awt.Color.WHITE);
	}
	
	public void update(Game game) {
		java.awt.Color color = game.toMove() == PieceColor.BLACK ? java.awt.Color.BLACK : java.awt.Color.WHITE;
		this.setBackground(color);
	}
}
