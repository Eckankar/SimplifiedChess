package view;

import java.awt.Component;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.*;
import model.pieces.*;

import control.*;

/**
 * A chess application window consisting of a chess board and a .  
 * It can show the state of an associated game of simplified chess
 * and forwards user button clicks to an associated GUI controller.
 */
public class ChessApplicationWindow extends JFrame implements Observer {
	
	private static final long serialVersionUID = -7816581038129851012L;

	private ChessBoardPanel chessBoardPanel;
	private PlayerPanel blackPlayerPanel;
	private PlayerPanel whitePlayerPanel;
	private TurnPanel turnPanel;
	private MoveLogPanel moveLogPanel;
	private JButton blackPlayButton, whitePlayButton;
	
	public ChessApplicationWindow(GUIController guiController) {
		super("Chess");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));
		this.setResizable(false);
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chessBoardPanel = new ChessBoardPanel(guiController);
		add(chessBoardPanel);
		blackPlayerPanel = new PlayerPanel(PieceColor.BLACK);
		turnPanel = new TurnPanel();
		whitePlayerPanel = new PlayerPanel(PieceColor.WHITE);
	    whitePlayButton = new JButton("Play white!");
		whitePlayButton.addActionListener(guiController);
		whitePlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        blackPlayButton = new JButton("Play black!");
		blackPlayButton.addActionListener(guiController);
		blackPlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
		playerPanel.add(blackPlayerPanel);
		playerPanel.add(blackPlayButton);
		playerPanel.add(turnPanel);
		playerPanel.add(whitePlayerPanel);
		playerPanel.add(whitePlayButton);
		add(playerPanel);
		moveLogPanel = new MoveLogPanel();
		add(moveLogPanel);
		JMenu menu = new JMenu("File");
		menu.add("New game");
		menu.add("Load game");
		menu.add("Save game");
		menu.addSeparator();
		menu.add(new JMenuItem("Quit",6));
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);
		pack();
		setVisible(true);
		
	}

	/**
	 * Update the chess window based on information from a game manager
	 */
	public void update(Observable obs, Object arg) {
		GameManager gameManager = (GameManager) obs;
		Game game = gameManager.game;
		
		// synchronize display with game state
		chessBoardPanel.update(game.position());
		turnPanel.update(game);
		moveLogPanel.update(game);
		
		// disable input and display message if game is over
		if (game.gameOver()) {
			chessBoardPanel.setBoardEnabled(false); // disable input from chess board
			PieceColor winner = game.winner();
			String message = winner == null ? "Remis" : winner.toString() + " wins after " + game.numMoves() + " moves";
			whitePlayButton.setEnabled(false);
			blackPlayButton.setEnabled(false);
			JOptionPane.showMessageDialog(this, "Game over: " + message, "End of game", JOptionPane.ERROR_MESSAGE);
		}
	}

}
