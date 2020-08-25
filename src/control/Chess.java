package control;

import model.*;
import model.board.generators.*;
import model.players.*;
import view.ChessApplicationWindow;

/**
 * A GUI-based program for playing simplified chess
 */
public class Chess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameManager gameController = new GameManager(
			new SimpleChessGame(
					new StandardChessPositionFactory() // standard chess position
			)
		);
		ChessGUIController guiController = new ChessGUIController(gameController);
		ChessApplicationWindow chessApplicationWindow = new ChessApplicationWindow(guiController);
		Player white = null;
		Player black = null;
		//white = new RandomPlayer("Cleve");
		white = new MinimaxPlayer("Panda");
		//black = new CleverRandomPlayer("Randy");
		black = new MinimaxPlayer("Pop Chef");
		gameController.setPlayers(white, black);
		gameController.addObserver(chessApplicationWindow);
		chessApplicationWindow.update(gameController, null);
		chessApplicationWindow.pack();
		chessApplicationWindow.setVisible(true);
	}

}
