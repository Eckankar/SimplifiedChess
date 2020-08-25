package model.board.generators;

/**
 * Generates an empty chess board.
 * @author Sebastian Paaske Tørholm
 */
public class EmptyPositionFactory extends GenericPositionFactory {
	/**
	 * Creates a new StandardChessPositionFactory.
	 */
	public EmptyPositionFactory() {
		super(new String[][] {
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" }, // 1
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" }, // 2
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" },
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" },
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" },
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" },
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" }, // 7
			{ "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" ,  "" }  // 8
		});
	}
}
