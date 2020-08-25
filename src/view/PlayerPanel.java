package view;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.pieces.*;

/**
 * A panel with information on the players of a game of simplified chess
 */
public class PlayerPanel extends JPanel {

	private static final long serialVersionUID = 1817465285110139219L;

	public PlayerPanel(PieceColor color) {
		setBorder(new TitledBorder(color.toString().toLowerCase() + " player"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		java.awt.Color playerColor = color == PieceColor.BLACK ? java.awt.Color.BLACK : java.awt.Color.WHITE;
		java.awt.Color textColor = color == PieceColor.BLACK ? java.awt.Color.WHITE : java.awt.Color.BLACK;
		JLabel colorLabel = new JLabel("<html><font size=+2>" + color.toString() + "</font></html>", JLabel.CENTER);
		colorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		colorLabel.setOpaque(true);
		colorLabel.setBackground(playerColor);
		colorLabel.setForeground(textColor);
		add(colorLabel);
		// setVisible(false);
	}
}
