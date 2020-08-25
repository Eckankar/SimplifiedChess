package model.board.generators;

/**
 * Generates a standard chess setup.
 * @author Sebastian Paaske Tørholm
 */
public class StandardChessPositionFactory extends GenericPositionFactory {
	/**
	 * Creates a new StandardChessPositionFactory.
	 */
	public StandardChessPositionFactory() {
		super(new String[][] {
			{"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"}, // 1
			{"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"}, // 2
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" },
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" },
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" },
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" },
			{"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"}, // 7
			{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"}  // 8
		});
	}
}
