package view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

import model.Game;

/**
 * Displays a log of a chess game.
 * @author Sebastian Paaske Tørholm
 */
public class MoveLogPanel extends JPanel {
	private static final long serialVersionUID = -5207378232336843769L;
	private TableModel moveLogModel;
	private JTable moveLogTable;
	private int moveCount;
	
	/**
	 * Create a new MoveLogPanel.
	 */
	public MoveLogPanel() {
		moveLogModel = new DefaultTableModel(100, 2);
		TableColumnModel cols = new DefaultTableColumnModel();
		{
			TableColumn white = new TableColumn(0);
			white.setHeaderValue("White");
			cols.addColumn(white);
			TableColumn black = new TableColumn(1);
			black.setHeaderValue("Black");
			cols.addColumn(black);
		}
		moveLogTable = new JTable(moveLogModel, cols);
		moveLogModel.addTableModelListener(moveLogTable);
		moveLogTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		moveLogTable.setPreferredScrollableViewportSize( // ugly as all hell, but what can I do
				new Dimension(                           // -- it's swing.
					moveLogTable.getPreferredSize().width,
					moveLogTable.getPreferredScrollableViewportSize().height
				)
		);
		JScrollPane scroller = new JScrollPane(moveLogTable);
		this.add(scroller);
		
		moveCount = 0;
	}
	
	/**
	 * Send a game update.
	 * @param game game that has updated
	 */
	public void update(Game game) {
		int numMoves = game.numMoves();
		if (numMoves > moveCount) {
			moveCount = numMoves;
			int row = (numMoves-1)/2; 
			int col = (numMoves-1) % 2;
			moveLogModel.setValueAt(game.lastMove().inACN(), row, col);
			moveLogTable.scrollRectToVisible(moveLogTable.getCellRect(row, col, true));
		}
	}
}
