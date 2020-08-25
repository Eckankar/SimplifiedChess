package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.board.SquareFactory;
import model.board.IntSquareFactory;
import model.board.Position;
import model.pieces.*;

/**
 * A Swing panel displaying a chess board, used as a component in a chess application window
 */
public class ChessBoardPanel extends JPanel {
	
	private static final long serialVersionUID = 1183987522338376335L;
	
	private final Color dark;
	private final Color light;
	private ChessSquareButton[][] board;
	private SquareFactory squareFactory = new IntSquareFactory(); // change, if other implementation of squares is desired
	
	public ChessBoardPanel (ItemListener listener) {
		dark = Color.GRAY;
		light = Color.LIGHT_GRAY;
		Color[] colors = {dark, light};
		Dimension size = new Dimension(500, 500);
		setSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);		
		this.setLayout(new GridLayout(9,9));
		this.setBorder(new TitledBorder("Board"));
		board = new ChessSquareButton[8][8];
		for (int rank = 8; rank >= 1; --rank) {
			this.add(new JLabel(Integer.toString(rank), JLabel.CENTER));
			for (int file = 1; file <= 8; ++file) {
				ChessSquareButton square = new ChessSquareButton(squareFactory.create(file, rank));
				square.setSize(50, 50);
				square.setBackground(colors[(file+rank)%2]);	
				square.addItemListener(listener);
				this.add(square);
				board[file-1][rank-1] = square;
			}
		}
		this.add(new JLabel());
		for (int file = 1; file <= 8; ++file) {
			this.add(new JLabel(Character.toString( (char)('A'+file-1) ), JLabel.CENTER));
		}
	}
	
	/**
	 * Update the chess board, based on a position in a game of simplified chess
	 * @param position The position of the game of simplified chess
	 */
	public void update(Position position) {
		for (int rank = 8; rank >= 1; --rank) {
			for (int file = 1; file <= 8; ++file) {
				Piece piece = position.get(file, rank);
				ImageIcon icon = null;
				if (piece != null) {
					PieceColor color = piece.color();
					PieceType type = piece.type();
					
					java.net.URL iconURL = 
						getClass().getResource("/data/"
						                    	+ color.toString().toLowerCase() + "_"
					                    		+ type.toString().toLowerCase() + ".png");
					icon = new ImageIcon(iconURL);
				}
				ChessSquareButton square = board[file - 1][rank - 1];
				square.setIcon(icon);
				square.setSelected(false);
			}
		}
	}
	
	/**
	 * Make sure all chess fiels are deselected
	 */
	public void resetBoard() {
		for (int rank = 8; rank >= 1; --rank) {
			for (int file = 1; file <= 8; ++file) {
				board[file - 1][rank - 1].setSelected(false);
			}
		}
	}
	
	/**
	 * En- or disable the board
	 * @param enabled Whether the board is to enabled for input (true) or disabled (false)
	 */
	public void setBoardEnabled(boolean enabled) {
		for (int rank = 8; rank >= 1; --rank) {
			for (int file = 1; file <= 8; ++file) {
				board[file - 1][rank - 1].setEnabled(enabled);
			}
		}
	}
	
}
