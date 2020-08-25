package model.board.generators;

import java.util.List;
import model.board.*;
import model.pieces.*;

/**
 * A PositionFactory is an object that generates a starting position for a game.
 * @author Sebastian Paaske Tørholm
 */
public interface PositionFactory {
	/**
	 * Gets the starting position provided by this PositionFactory.
	 * @return the starting position provided by this PositionFactory
	 */
	public Position position();

	/**
	 * Gets the black pieces the starting position of this PositionFactory contains.
	 * @return all black pieces on position()
	 */
	public List<Piece> blackPieces();
	
	/**
	 * Gets the white pieces the starting position of this PositionFactory contains.
	 * @return all white pieces on position()
	 */
	public List<Piece> whitePieces();
	
	/**
	 * Gets all the pieces the starting position of this PositionFactory contains.
	 * @return all pieces on position()
	 */
	public List<Piece> allPieces();
}
